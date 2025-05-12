package pers.nefedov.subscriptions.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pers.nefedov.subscriptions.dto.UserCreationDto;
import pers.nefedov.subscriptions.dto.UserDto;
import pers.nefedov.subscriptions.dto.UserSubscriptionDto;
import pers.nefedov.subscriptions.entity.User;
import pers.nefedov.subscriptions.exception.ConflictException;
import pers.nefedov.subscriptions.repo.UserRepository;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserSubscriptionService userSubscriptionService;


    public UserServiceImpl(UserRepository userRepository, UserSubscriptionService userSubscriptionService) {
        this.userRepository = userRepository;
        this.userSubscriptionService = userSubscriptionService;
    }

    @Override
    public UserDto createUser(UserCreationDto userCreationDto) {
        MDC.put("operation", "createUser");
        log.info("Attempt to create user with email: {}", maskEmail(userCreationDto.getEmail()));
        try {
            User user = mapToUser(userCreationDto);
            User savedUser = userRepository.save(user);
            log.debug("User created successfully: userId={}", savedUser.getId());
            return mapToUserDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            log.error("User creation failed - email already exists: {}", maskEmail(userCreationDto.getEmail()));
            throw new ConflictException("Email already in use");
        } finally {
            MDC.remove("operation");
        }
    }

    @Override
    @Transactional
    public UserDto getUser(long id) {
        MDC.put("userId", String.valueOf(id));
        try {
            log.debug("Fetching user by id: {}", id);

            return userRepository.findById(id)
                    .map(user -> {
                        log.debug("User found: {}", user.getEmail());
                        return mapToUserDto(user);
                    })
                    .orElseThrow(() -> {
                        log.warn("User not found: id={}", id);
                        return new EntityNotFoundException("User not found");
                    });
        } finally {
            MDC.remove("userId");
        }
    }

    @Override
    @Transactional
    public UserDto updateUser(long id, UserCreationDto dto) {
        MDC.put("userId", String.valueOf(id));
        MDC.put("operation", "updateUser");
        try {
            log.info("Updating user ID: {}, new email: {}", id, maskEmail(dto.getEmail()));

            User user = userRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Update failed - user not found: ID={}", id);
                        return new EntityNotFoundException("User not found");
                    });

            user.setEmail(dto.getEmail());
            user.setName(dto.getName());
            User updatedUser = userRepository.saveAndFlush(user);

            log.debug("User updated successfully: {}", updatedUser);
            return mapToUserDto(updatedUser);
        } finally {
            MDC.remove("userId");
            MDC.remove("operation");
        }
    }

    @Override
    @Transactional

    public void deleteUser(long id) {
        MDC.put("userId", String.valueOf(id));
        try {
            log.warn("Initiating user deletion: ID={}", id);

            User user = userRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("User not found for deletion: ID={}", id);
                        return new EntityNotFoundException("User not found");                    });


            List<UserSubscriptionDto> subscriptions = userSubscriptionService.getSubscriptions(user.getId());
            log.debug("Found {} subscriptions to remove", subscriptions.size());

            subscriptions.forEach(sub -> {
                try {
                    userSubscriptionService.deleteSubscription(
                            sub.userId(),
                            sub.subscriptionId()
                    );
                    log.trace("Removed subscription: {}", sub.subscriptionId());
                } catch (Exception e) {
                    log.error("Failed to remove subscription {}: {}", sub.subscriptionId(), e.getMessage());
                }
            });

            userRepository.delete(user);
            log.info("Successfully deleted user with all subscriptions: ID={}", id);

        } catch (Exception e) {
            log.error("User deletion failed: ID={}, reason={}", id, e.getMessage());
            throw new DataIntegrityViolationException("User deletion failed");
        } finally {
            MDC.remove("userId");
        }
    }

    private static UserDto mapToUserDto(User savedUser) {
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setEmail(savedUser.getEmail());
        userDto.setName(savedUser.getName());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        return userDto;
    }

    private static User mapToUser(UserCreationDto userCreationDto) {
        User user = new User();
        user.setEmail(userCreationDto.getEmail());
        user.setName(userCreationDto.getName());
        return user;
    }

    private String maskEmail(String email) {
        return email.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");  // j***@example.com
    }
}


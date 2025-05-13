package pers.nefedov.subscriptions.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import pers.nefedov.subscriptions.dto.UserSubscriptionDto;
import pers.nefedov.subscriptions.entity.Subscription;
import pers.nefedov.subscriptions.entity.User;
import pers.nefedov.subscriptions.entity.UserSubscription;
import pers.nefedov.subscriptions.entity.UserSubscriptionId;
import pers.nefedov.subscriptions.mapper.UserSubscriptionMapper;
import pers.nefedov.subscriptions.repo.SubscriptionRepository;
import pers.nefedov.subscriptions.repo.UserRepository;
import pers.nefedov.subscriptions.repo.UserSubscriptionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserSubscriptionServiceImpl implements UserSubscriptionService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserSubscriptionMapper userSubscriptionMapper;

    @Override
    public UserSubscriptionDto addSubscriptionToUser(Long userId, Long subscriptionId) {
        MDC.put("userId", "" + userId);
        MDC.put("subscriptionId", "" + subscriptionId);
        MDC.put("operation", "addSubscription");

        try {
            log.info("Attempting to add subscription to user");
            User user = getUserOrThrow(userId);
            Subscription subscription = getSubscriptionOrThrow(subscriptionId);

            if (userSubscriptionRepository.existsByUserAndSubscription(user, subscription)) {
                log.warn("Subscription already exists for user: userId={}, subscriptionId={}", userId, subscriptionId);
                throw new IllegalStateException("User already has this subscription");
            }
            UserSubscription userSubscription = userSubscriptionMapper.toUserSubscription(user, subscription);
            UserSubscription saved = userSubscriptionRepository.saveAndFlush(userSubscription);
            log.debug("Subscription added: {}", saved);
            user.getSubscriptions().add(saved);
            subscription.getUserSubscriptions().add(saved);
            log.info("Successfully added subscription to user");
            return userSubscriptionMapper.toUserSubscriptionDto(saved);
        } finally {
            MDC.remove("userId");
            MDC.remove("subscriptionId");
            MDC.remove("operation");
        }
    }

    @Override
    public List<UserSubscriptionDto> getSubscriptions(Long userId) {
        MDC.put("userId", userId.toString());
        MDC.put("operation", "getSubscriptions");
        getUserOrThrow(userId);
        try {
            log.debug("Fetching subscriptions for user");
            List<UserSubscription> result = userSubscriptionRepository.getAllByUser_Id(userId);
            log.debug("Found {} subscriptions for user", result.size());
            return result.stream().map(userSubscriptionMapper::toUserSubscriptionDto).collect(Collectors.toList());
        } finally {
            MDC.remove("userId");
            MDC.remove("operation");
        }
    }

    @Override
    @Transactional
    public void deleteSubscription(Long userId, Long subId) {
        MDC.put("userId", userId.toString());
        MDC.put("subscriptionId", subId.toString());
        MDC.put("operation", "deleteSubscription");

        try {
            log.info("Attempting to delete subscription: subscriptionId={}", subId);
            User user = getUserOrThrow(userId);
            Subscription subscription = getSubscriptionOrThrow(subId);
            UserSubscription deletingSub = userSubscriptionRepository.findByUserAndSubscription(user, subscription);
            user.getSubscriptions().remove(deletingSub);
            subscription.getUserSubscriptions().remove(deletingSub);
            userSubscriptionRepository.deleteById(new UserSubscriptionId(userId, subId));

            log.info("Successfully deleted subscription");
        } finally {
            MDC.remove("userId");
            MDC.remove("subscriptionId");
            MDC.remove("operation");
        }
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.error("User not found: userId={}", userId);
            return new EntityNotFoundException("User not found");
        });
    }

    private Subscription getSubscriptionOrThrow(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId).orElseThrow(() -> {
            log.error("Subscription not found: subscriptionId={}", subscriptionId);
            return new EntityNotFoundException("Subscription not found");
        });
    }
}
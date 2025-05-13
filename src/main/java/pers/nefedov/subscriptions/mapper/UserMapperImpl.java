package pers.nefedov.subscriptions.mapper;

import org.springframework.stereotype.Component;
import pers.nefedov.subscriptions.dto.UserCreationDto;
import pers.nefedov.subscriptions.dto.UserDto;
import pers.nefedov.subscriptions.entity.User;
@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User mapToUser(UserCreationDto userCreationDto) {
        User user = new User();
        user.setEmail(userCreationDto.getEmail());
        user.setName(userCreationDto.getName());
        return user;
    }

    @Override
    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        return userDto;
    }
}

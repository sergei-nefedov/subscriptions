package pers.nefedov.subscriptions.mapper;

import pers.nefedov.subscriptions.dto.UserCreationDto;
import pers.nefedov.subscriptions.dto.UserDto;
import pers.nefedov.subscriptions.entity.User;

public interface UserMapper {
    User mapToUser(UserCreationDto userCreationDto);
    UserDto mapToUserDto(User user);
}

package pers.nefedov.subscriptions.service;

import pers.nefedov.subscriptions.dto.UserCreationDto;
import pers.nefedov.subscriptions.dto.UserDto;

public interface UserService {

    UserDto createUser(UserCreationDto userCreationDto);

    UserDto getUser(long id);

    UserDto updateUser(long id, UserCreationDto userCreationDto);

    void deleteUser(long id);

}

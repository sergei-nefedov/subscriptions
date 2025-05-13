package pers.nefedov.subscriptions.service;

import pers.nefedov.subscriptions.dto.UserCreationDto;
import pers.nefedov.subscriptions.dto.UserDto;

public interface UserService {

    UserDto createUser(UserCreationDto userCreationDto);

    UserDto getUser(Long id);

    UserDto updateUser(Long id, UserCreationDto userCreationDto);

    void deleteUser(Long id);

}

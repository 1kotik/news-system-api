package by.kotik.userservice.service;

import by.kotik.userservice.dto.PasswordDto;
import by.kotik.userservice.dto.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto getUserById(UUID userId);
    UserDto getUserByLogin(String login);
    UserDto changeUsername(UUID userId, String newUsername);
    UserDto changePassword(UUID userId, PasswordDto passwordDto);
}

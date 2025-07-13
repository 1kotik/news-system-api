package by.kotik.userservice.service;

import by.kotik.userservice.dto.PasswordDto;
import by.kotik.userservice.dto.UserDto;
import dto.TokenDto;

import java.util.UUID;

public interface UserService {
    UserDto getUserById(UUID userId);
    UserDto getUserByLogin(String login);
    TokenDto changeUsername(String login, String newUsername);
    UserDto changePassword(UUID userId, PasswordDto passwordDto);
}

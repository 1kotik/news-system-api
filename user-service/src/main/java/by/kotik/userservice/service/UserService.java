package by.kotik.userservice.service;

import by.kotik.userservice.dto.PasswordDto;
import by.kotik.userservice.dto.UserCreationDto;
import by.kotik.userservice.dto.UserDto;
import by.kotik.userservice.dto.UserNicknameAndEmailDto;

import java.util.UUID;

public interface UserService {
    UserDto getUserById(UUID userId);
    UserDto getUserByNickname(String nickname);
    UserDto getUserByEmail(String email);
    UserNicknameAndEmailDto createUser(UserCreationDto userCreationDto);
    UserDto changeNickname(UUID userId, String newNickname);
    boolean doesUserExist(String email);
    UserDto changePassword(UUID userId, PasswordDto passwordDto);
}

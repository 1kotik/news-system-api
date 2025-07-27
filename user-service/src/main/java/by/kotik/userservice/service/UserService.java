package by.kotik.userservice.service;

import by.kotik.userservice.dto.PasswordDto;
import by.kotik.userservice.dto.UserDto;
import by.kotik.userservice.dto.UserInformationDto;
import dto.TokenDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    UserInformationDto getUserInformationById(UUID userId);
    UserInformationDto getUserByLogin(String login);
    TokenDto changeUsername(String login, String newUsername);
    UserDto changePassword(UUID userId, PasswordDto passwordDto);
}

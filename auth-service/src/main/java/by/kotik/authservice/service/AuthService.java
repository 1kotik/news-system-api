package by.kotik.authservice.service;

import by.kotik.authservice.dto.ChangePasswordDto;
import by.kotik.authservice.dto.UserRegistrationDto;
import dto.TokenDto;
import dto.UserAuthenticationDto;
import dto.UserAuthorizationDto;

public interface AuthService {
    TokenDto login(UserAuthenticationDto userAuthenticationDto);
    TokenDto register(UserRegistrationDto userRegistrationDto);
    TokenDto changePassword(ChangePasswordDto changePasswordDto, UserAuthorizationDto userAuthorizationDto);
}

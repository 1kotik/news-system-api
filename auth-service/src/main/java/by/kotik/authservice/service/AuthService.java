package by.kotik.authservice.service;

import by.kotik.authservice.dto.UserAuthenticationDto;
import by.kotik.authservice.dto.UserRegistrationDto;
import dto.TokenDto;

public interface AuthService {
    TokenDto login(UserAuthenticationDto userAuthenticationDto);
    TokenDto register(UserRegistrationDto userRegistrationDto);
}

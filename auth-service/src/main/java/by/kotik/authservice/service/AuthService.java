package by.kotik.authservice.service;

import by.kotik.authservice.dto.UserAuthenticationDto;
import by.kotik.authservice.dto.UserRegistrationDto;

public interface AuthService {
    String login(UserAuthenticationDto userAuthenticationDto);
    String register(UserRegistrationDto userRegistrationDto);
}

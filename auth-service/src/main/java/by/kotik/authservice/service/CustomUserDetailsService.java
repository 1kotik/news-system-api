package by.kotik.authservice.service;

import dto.UserAuthorizationDto;
import dto.UserRegistrationTransitiveDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    UserAuthorizationDto createUser(UserRegistrationTransitiveDto userRegistrationDto);
    UserAuthorizationDto changePassword(String login, String newPassword);
}

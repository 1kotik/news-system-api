package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserCreationDto;
import dto.UserAuthenticationDto;
import dto.UserAuthorizationDto;
import dto.UserDetailsDto;
import jakarta.validation.Valid;

public interface InternalUserService {
    boolean doesUserExist(String email);
    UserDetailsDto getUserDetailsByLogin(String login);
    UserAuthorizationDto createUser(@Valid UserCreationDto userCreationDto);
    UserAuthorizationDto changePassword(UserAuthenticationDto userAuthenticationDto);
}

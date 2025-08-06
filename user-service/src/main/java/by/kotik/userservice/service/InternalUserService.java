package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserCreationDto;
import dto.UserAuthenticationDto;
import dto.UserAuthorizationDto;
import dto.UserDetailsDto;
import dto.UserPreviewDto;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface InternalUserService {
    boolean doesUserExist(String email);
    UserDetailsDto getUserDetailsByLogin(String login);
    UserAuthorizationDto createUser(@Valid UserCreationDto userCreationDto);
    UserAuthorizationDto changePassword(UserAuthenticationDto userAuthenticationDto);
    Map<UUID, UserPreviewDto> getUserPreviews(Set<UUID> userIds);
}

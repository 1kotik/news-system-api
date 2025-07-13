package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserProfileDto;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {
    UserProfileDto findUserProfileByLogin(String login);
    UserProfileDto updateUserProfile(String login, UserProfileDto userProfileDto);
}

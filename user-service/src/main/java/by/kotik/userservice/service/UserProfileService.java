package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserProfileDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserProfileService {
    UserProfileDto findUserProfileByUserId(UUID userId);
    UserProfileDto updateUserProfile(UUID userID, UserProfileDto userProfileDto);
}

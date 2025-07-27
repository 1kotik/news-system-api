package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserProfileDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public interface UserProfileService {
    UserProfileDto findUserProfileByLogin(String login);
    UserProfileDto updateUserProfile(String login, UserProfileDto userProfileDto);
    UserProfileDto setAvatar(MultipartFile file, String login);
}

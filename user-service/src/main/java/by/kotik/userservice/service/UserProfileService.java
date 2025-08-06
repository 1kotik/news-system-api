package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserProfileDto;
import by.kotik.userservice.dto.UserPublicInfoDto;
import dto.UserPreviewDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public interface UserProfileService {
    UserProfileDto findUserProfileByLogin(String login);
    UserProfileDto updateUserProfile(String login, UserPublicInfoDto userPublicInfoDto);
    UserProfileDto setAvatar(MultipartFile file, String login);
}

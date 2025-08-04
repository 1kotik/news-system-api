package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserProfileDto;
import by.kotik.userservice.dto.UserPublicInfoDto;
import by.kotik.userservice.entity.UserProfile;
import by.kotik.userservice.mapper.UserProfileMapper;
import by.kotik.userservice.repository.UserProfileRepository;
import exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import service.FileStorageService;

@RequiredArgsConstructor
@Service
public class DefaultUserProfileService implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    @Qualifier("localFileStorageService")
    private final FileStorageService fileStorageService;
    @Value("${file-storage.local.service.avatar-folder-name}")
    private String avatarFolderName;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto findUserProfileByLogin(String login) {
        return userProfileRepository.findByLogin(login)
                .map(userProfileMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(String login, UserPublicInfoDto userPublicInfoDto) {
        UserProfile userProfile = userProfileRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
        userProfileMapper.updateUser(userPublicInfoDto, userProfile);
        return userProfileMapper.toDto(userProfileRepository.save(userProfile));
    }

    @Override
    @Transactional
    public UserProfileDto setAvatar(MultipartFile file, String login) {
        UserProfile userProfile = userProfileRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));

        String avatarUrl = fileStorageService.uploadFile(file, avatarFolderName, login);

        userProfile.setAvatar(avatarUrl);
        userProfileRepository.save(userProfile);

        return userProfileMapper.toDto(userProfile);
    }
}

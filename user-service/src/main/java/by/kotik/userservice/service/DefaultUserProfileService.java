package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserProfileDto;
import by.kotik.userservice.entity.UserProfile;
import by.kotik.userservice.mapper.UserProfileMapper;
import by.kotik.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DefaultUserProfileService implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto findUserProfileByUserId(UUID userId) {
        return userProfileRepository.findByUserId(userId)
                .map(userProfileMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Profile Not Found"));
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(UUID userID, UserProfileDto userProfileDto) {
        UserProfile userProfile = userProfileRepository.findByUserId(userID)
                .orElseThrow(() -> new NoSuchElementException("Profile Not Found"));
        userProfileMapper.updateUser(userProfileDto, userProfile);
        return userProfileMapper.toDto(userProfileRepository.save(userProfile));
    }
}

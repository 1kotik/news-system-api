package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserProfileDto;
import by.kotik.userservice.entity.UserProfile;
import by.kotik.userservice.mapper.UserProfileMapper;
import by.kotik.userservice.repository.UserProfileRepository;
import exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DefaultUserProfileService implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto findUserProfileByLogin(String login) {
        return userProfileRepository.findByLogin(login)
                .map(userProfileMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(String login, UserProfileDto userProfileDto) {
        UserProfile userProfile = userProfileRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
        userProfileMapper.updateUser(userProfileDto, userProfile);
        return userProfileMapper.toDto(userProfileRepository.save(userProfile));
    }
}

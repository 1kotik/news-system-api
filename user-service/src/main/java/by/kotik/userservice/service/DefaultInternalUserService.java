package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserCreationDto;
import by.kotik.userservice.entity.User;
import by.kotik.userservice.mapper.UserMapper;
import by.kotik.userservice.mapper.UserProfileMapper;
import by.kotik.userservice.repository.UserProfileRepository;
import by.kotik.userservice.repository.UserRepository;
import dto.UserAuthenticationDto;
import dto.UserAuthorizationDto;
import dto.UserDetailsDto;
import dto.UserPreviewDto;
import exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class DefaultInternalUserService implements InternalUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean doesUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailsDto getUserDetailsByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userMapper::userToUserDetailsDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public UserAuthorizationDto createUser(@Valid UserCreationDto userCreationDto) {
        User user = userMapper.userCreationDtoToUser(userCreationDto);
        user.setRoles(new ArrayList<>(List.of(roleService.getRoleByName("ROLE_USER"))));
        user.getUserProfile().setUser(user);

        User savedUser = userRepository.save(user);

        return userMapper.userToUserAuthorizationDto(savedUser);
    }

    @Override
    public UserAuthorizationDto changePassword(UserAuthenticationDto userAuthenticationDto) {
        User user = userRepository.findByLogin(userAuthenticationDto.getLogin())
                .orElseThrow(() -> new UserNotFoundException(userAuthenticationDto.getLogin()));
        user.setPassword(userAuthenticationDto.getPassword());

        User savedUser = userRepository.save(user);

        return userMapper.userToUserAuthorizationDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<UUID, UserPreviewDto> getUserPreviews(Set<UUID> userIds) {
        return userProfileRepository.findAllByUserIds(userIds)
                .stream()
                .map(profile -> userProfileMapper.toUserPreviewDto(profile, userRepository))
                .collect(Collectors.toMap(UserPreviewDto::getUserId, userPreviewDto -> userPreviewDto));
    }
}

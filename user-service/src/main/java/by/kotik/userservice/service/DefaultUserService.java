package by.kotik.userservice.service;

import by.kotik.userservice.dto.PasswordDto;
import by.kotik.userservice.dto.UserDto;
import by.kotik.userservice.entity.User;
import by.kotik.userservice.exception.UsernameAlreadyExistsException;
import by.kotik.userservice.mapper.UserMapper;
import by.kotik.userservice.repository.UserRepository;
import dto.TokenDto;
import dto.UserAuthorizationDto;
import exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.JwtUtils;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    @Transactional
    public TokenDto changeUsername(String login, String newUsername) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
        userRepository.findByUsername(newUsername)
                .ifPresent(existingUser -> {
                    throw new UsernameAlreadyExistsException(newUsername);
                });

        user.setUsername(newUsername);

        User updatedUser = userRepository.save(user);
        UserAuthorizationDto userAuthorizationDto = userMapper.userToUserAuthorizationDto(updatedUser);

        return jwtUtils.generateAuthenticationToken(userAuthorizationDto, Duration.ofMinutes(60));
    }

    @Override
    @Transactional
    public UserDto changePassword(UUID userId, PasswordDto passwordDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        user.setPassword(passwordDto.getPassword());
        return userMapper.userToUserDto(userRepository.save(user));
    }

}

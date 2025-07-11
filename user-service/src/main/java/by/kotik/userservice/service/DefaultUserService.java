package by.kotik.userservice.service;

import by.kotik.userservice.dto.PasswordDto;
import by.kotik.userservice.dto.UserDto;
import by.kotik.userservice.entity.User;
import by.kotik.userservice.mapper.UserMapper;
import by.kotik.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    @Transactional
    public UserDto changeUsername(UUID userId, String newUsername) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        user.setUsername(newUsername);
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto changePassword(UUID userId, PasswordDto passwordDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        user.setPassword(passwordDto.getPassword());
        return userMapper.userToUserDto(userRepository.save(user));
    }

}

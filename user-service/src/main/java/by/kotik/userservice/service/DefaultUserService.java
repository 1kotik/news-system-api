package by.kotik.userservice.service;

import by.kotik.userservice.dto.PasswordDto;
import by.kotik.userservice.dto.UserCreationDto;
import by.kotik.userservice.dto.UserDto;
import by.kotik.userservice.dto.UserNicknameAndEmailDto;
import by.kotik.userservice.entity.User;
import by.kotik.userservice.mapper.UserMapper;
import by.kotik.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
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
    public UserDto getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    @Transactional
    public UserNicknameAndEmailDto createUser(UserCreationDto userCreationDto) {
        User user = userMapper.userCreationDtoToUser(userCreationDto);
        user.setRoles(new HashSet<>(Set.of(roleService.getRoleByName("ROLE_USER"))));
        user.getUserProfile().setUser(user);
        userRepository.save(user);
        return userMapper.userCreationDtoToUserNicknameAndEmailDto(userCreationDto);
    }

    @Override
    @Transactional
    public UserDto changeNickname(UUID userId, String newNickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        user.setNickname(newNickname);
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean doesUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
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

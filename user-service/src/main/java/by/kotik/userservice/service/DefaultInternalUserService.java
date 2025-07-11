package by.kotik.userservice.service;

import by.kotik.userservice.dto.UserCreationDto;
import by.kotik.userservice.entity.User;
import by.kotik.userservice.mapper.UserMapper;
import by.kotik.userservice.repository.UserRepository;
import dto.UserAuthorizationDto;
import dto.UserDetailsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class DefaultInternalUserService implements InternalUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

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
}

package by.kotik.userservice.controller;

import by.kotik.userservice.dto.UserCreationDto;
import by.kotik.userservice.mapper.UserMapper;
import by.kotik.userservice.service.InternalUserService;
import dto.UserAuthorizationDto;
import dto.UserDetailsDto;
import dto.UserRegistrationTransitiveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/internal")
public class InternalUserController {
    private final InternalUserService internalUserService;
    private final UserMapper userMapper;

    @GetMapping("/{login}")
    public UserDetailsDto getUserByLogin(@PathVariable String login) {
        return internalUserService.getUserDetailsByLogin(login);
    }

    @GetMapping("/email/{email}")
    public boolean doesUserExistInternal(@PathVariable String email) {
        return internalUserService.doesUserExist(email);
    }

    @PostMapping
    public UserAuthorizationDto createUser(@RequestBody UserRegistrationTransitiveDto transitiveDto) {
        UserCreationDto userCreationDto = userMapper.transitiveDtoToUserCreationDto(transitiveDto);
        return internalUserService.createUser(userCreationDto);
    }
}

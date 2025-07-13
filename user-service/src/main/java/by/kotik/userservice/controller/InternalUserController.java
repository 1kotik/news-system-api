package by.kotik.userservice.controller;

import by.kotik.userservice.dto.UserCreationDto;
import by.kotik.userservice.mapper.UserMapper;
import by.kotik.userservice.service.InternalUserService;
import dto.UserAuthorizationDto;
import dto.UserDetailsDto;
import dto.UserRegistrationTransitiveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/internal")
public class InternalUserController {
    private final InternalUserService internalUserService;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('AUTH_SERVICE')")
    @GetMapping("/{login}")
    public UserDetailsDto getUserByLogin(@PathVariable String login) {
        return internalUserService.getUserDetailsByLogin(login);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('MAIL_SERVICE')")
    @GetMapping("/email/{email}")
    public boolean doesUserExist(@PathVariable String email) {
        return internalUserService.doesUserExist(email);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('AUTH_SERVICE')")
    @PostMapping
    public UserAuthorizationDto createUser(@RequestBody UserRegistrationTransitiveDto transitiveDto) {
        UserCreationDto userCreationDto = userMapper.transitiveDtoToUserCreationDto(transitiveDto);
        return internalUserService.createUser(userCreationDto);
    }
}

package by.kotik.userservice.controller;

import by.kotik.userservice.dto.PasswordDto;
import by.kotik.userservice.dto.UserDto;
import by.kotik.userservice.dto.UserInformationDto;
import by.kotik.userservice.service.UserService;
import dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{login}")
    public ResponseEntity<UserInformationDto> getUserByLogin(@PathVariable String login) {
        return ResponseEntity.ok(userService.getUserByLogin(login));
    }

    @PreAuthorize("hasRole('ADMIN') or #login eq authentication.details.username" +
            " or #login eq authentication.details.email")
    @PatchMapping("/username/{login}")
    public ResponseEntity<TokenDto> changeUsername(@PathVariable String login, @RequestParam String newUsername) {
        return ResponseEntity.ok(userService.changeUsername(login, newUsername));
    }

    @PreAuthorize("hasRole('ADMIN') or #userId eq principal")
    @PatchMapping("/password/{userId}")
    public ResponseEntity<UserDto> changePassword(@PathVariable UUID userId, @RequestBody PasswordDto passwordDto) {
        return ResponseEntity.ok(userService.changePassword(userId, passwordDto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserInformationDto> getCurrentUser(Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        return ResponseEntity.ok(userService.getUserInformationById(userId));
    }

}

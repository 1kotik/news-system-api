package by.kotik.userservice.controller;

import by.kotik.userservice.dto.PasswordDto;
import by.kotik.userservice.dto.UserDto;
import by.kotik.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/id/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable String login) {
        return ResponseEntity.ok(userService.getUserByLogin(login));
    }

    @PatchMapping("/username/{userId}")
    public ResponseEntity<UserDto> changeUsername(@PathVariable UUID userId, @RequestParam String newUsername) {
        return ResponseEntity.ok(userService.changeUsername(userId, newUsername));
    }

    @PatchMapping("/password/{userId}")
    public ResponseEntity<UserDto> changePassword(@PathVariable UUID userId, @RequestBody PasswordDto passwordDto) {
        return ResponseEntity.ok(userService.changePassword(userId, passwordDto));
    }

}

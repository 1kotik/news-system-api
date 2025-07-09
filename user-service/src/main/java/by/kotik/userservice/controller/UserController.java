package by.kotik.userservice.controller;

import by.kotik.userservice.dto.PasswordDto;
import by.kotik.userservice.dto.UserCreationDto;
import by.kotik.userservice.dto.UserDto;
import by.kotik.userservice.dto.UserNicknameAndEmailDto;
import by.kotik.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<UserDto> getUserByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.getUserByNickname(nickname));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserNicknameAndEmailDto> createUser(@RequestBody @Valid UserCreationDto userCreationDto) {
        return ResponseEntity.ok(userService.createUser(userCreationDto));
    }

    @PatchMapping("/nickname/{userId}")
    public ResponseEntity<UserDto> changeNickname(@PathVariable UUID userId, @RequestParam String newNickname) {
        return ResponseEntity.ok(userService.changeNickname(userId, newNickname));
    }

    @GetMapping("/internal/email/{email}")
    public boolean doesUserExistInternal(@PathVariable String email) {
        return userService.doesUserExist(email);
    }

    @PatchMapping("/password/{userId}")
    public ResponseEntity<UserDto> changePassword(@PathVariable UUID userId, @RequestBody PasswordDto passwordDto) {
        return ResponseEntity.ok(userService.changePassword(userId, passwordDto));
    }
}

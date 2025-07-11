package by.kotik.authservice.controller;

import by.kotik.authservice.dto.UserAuthenticationDto;
import by.kotik.authservice.dto.UserRegistrationDto;
import by.kotik.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserAuthenticationDto userAuthenticationDto) {
        return ResponseEntity.ok(authService.login(userAuthenticationDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.ok(authService.register(userRegistrationDto));
    }
}


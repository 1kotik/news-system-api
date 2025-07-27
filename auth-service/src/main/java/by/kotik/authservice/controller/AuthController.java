package by.kotik.authservice.controller;

import by.kotik.authservice.dto.ChangePasswordDto;
import by.kotik.authservice.dto.UserRegistrationDto;
import by.kotik.authservice.service.AuthService;
import dto.TokenDto;
import dto.UserAuthenticationDto;
import dto.UserAuthorizationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserAuthenticationDto userAuthenticationDto) {
        return ResponseEntity.ok(authService.login(userAuthenticationDto));
    }

    @PreAuthorize("hasAnyAuthority(#userRegistrationDto.email)")
    @PostMapping("/signup")
    public ResponseEntity<TokenDto> register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.ok(authService.register(userRegistrationDto));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/change-password")
    public ResponseEntity<TokenDto> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto,
                                                   Authentication authentication) {
        UserAuthorizationDto userAuthorizationDto = (UserAuthorizationDto) authentication.getDetails();
        return ResponseEntity.ok(authService.changePassword(changePasswordDto, userAuthorizationDto));
    }
}


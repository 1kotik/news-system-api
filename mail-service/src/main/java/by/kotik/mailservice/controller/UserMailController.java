package by.kotik.mailservice.controller;

import by.kotik.mailservice.annotation.UniqueEmail;
import by.kotik.mailservice.dto.ConfirmationCodeDto;
import by.kotik.mailservice.service.UserMailService;
import dto.TokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail/users")
public class UserMailController {
    private final UserMailService userMailService;

    @GetMapping("/registration-code/{email}")
    public ResponseEntity<TokenDto> getRegistrationCode(@Valid @UniqueEmail @PathVariable("email") String email) {
        return ResponseEntity.ok(userMailService.generateConfirmationCode(email));
    }

    @PreAuthorize("hasAuthority(T(String).valueOf(#confirmationCodeDto.code)) and hasAuthority(#confirmationCodeDto.email)")
    @GetMapping("/registration-code")
    public ResponseEntity<TokenDto> checkConfirmationCode
            (@RequestBody @Valid ConfirmationCodeDto confirmationCodeDto) {
        return ResponseEntity.ok(userMailService.checkConfirmationCode(confirmationCodeDto));
    }
}

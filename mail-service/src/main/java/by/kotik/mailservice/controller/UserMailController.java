package by.kotik.mailservice.controller;

import by.kotik.mailservice.dto.ConfirmationCodeDto;
import by.kotik.mailservice.service.UserMailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail/users")
public class UserMailController {
    private final UserMailService userMailService;

    @PostMapping("/registration-code/{email}")
    public ResponseEntity<Void> getRegistrationCode(@PathVariable("email") String email) {
        userMailService.generateConfirmationCode(email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/registration-code")
    public ResponseEntity<ConfirmationCodeDto> checkConfirmationCode
            (@RequestBody @Valid ConfirmationCodeDto confirmationCodeDto) {
        return ResponseEntity.ok(userMailService.checkConfirmationCode(confirmationCodeDto));
    }
}

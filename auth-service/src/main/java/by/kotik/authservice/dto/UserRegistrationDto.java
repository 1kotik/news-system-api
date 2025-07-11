package by.kotik.authservice.dto;

import by.kotik.authservice.annotation.PasswordsMatch;
import by.kotik.authservice.annotation.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordsMatch
public class UserRegistrationDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4)
    @Username
    private String username;

    @Size(min = 8)
    private String password;

    private String confirmPassword;
}

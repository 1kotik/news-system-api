package by.kotik.authservice.dto;

import by.kotik.authservice.annotation.PasswordsMatch;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordsMatch(message = "Passwords do not match")
public class PasswordDto {
    @Size(min = 8, message = "Invalid password. Size must be at least 8 characters")
    private String password;

    private String confirmPassword;
}

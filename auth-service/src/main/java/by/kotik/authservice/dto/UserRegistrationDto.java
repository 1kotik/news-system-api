package by.kotik.authservice.dto;

import by.kotik.authservice.annotation.PasswordsMatch;
import by.kotik.authservice.annotation.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordsMatch(message = "Passwords do not match")
public class UserRegistrationDto {
    @Email(message = "Invalid email")
    @NotBlank(message = "Provide email")
    private String email;

    @NotBlank
    @Size(min = 4, message = "Invalid username. Size must be at least 4 characters")
    @Username(message = "Invalid username. Should consist of letters, digits and '_'")
    private String username;

    @Size(min = 8, message = "Invalid password. Size must be at least 8 characters")
    private String password;

    private String confirmPassword;
}

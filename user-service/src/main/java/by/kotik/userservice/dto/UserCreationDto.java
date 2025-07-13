package by.kotik.userservice.dto;

import by.kotik.userservice.annotation.UniqueEmail;
import by.kotik.userservice.annotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreationDto {
    @NotBlank(message = "Provide email")
    @Email(message = "Invalid email")
    @UniqueEmail
    private String email;
    @NotBlank(message = "Provide username")
    @UniqueUsername
    private String username;
    @NotBlank(message = "Provide password")
    private String password;
}

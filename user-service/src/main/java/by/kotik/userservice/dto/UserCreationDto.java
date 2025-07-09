package by.kotik.userservice.dto;

import by.kotik.userservice.annotation.UniqueCredentials;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@UniqueCredentials
public class UserCreationDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
}

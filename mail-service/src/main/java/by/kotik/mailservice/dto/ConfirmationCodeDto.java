package by.kotik.mailservice.dto;

import by.kotik.mailservice.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationCodeDto {
    @UniqueEmail
    @Email(message = "Invalid email")
    @NotBlank(message = "Provide email")
    private String email;
    private int code;
    private ZonedDateTime expiredAt;
}

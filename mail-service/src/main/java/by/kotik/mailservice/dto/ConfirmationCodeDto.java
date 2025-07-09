package by.kotik.mailservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ConfirmationCodeDto {
    @Email
    @NotBlank
    private String email;
    private int code;
    private ZonedDateTime expiredAt;
}

package by.kotik.mailservice.dto;

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
    @Email
    @NotBlank
    private String email;
    private int code;
    private ZonedDateTime expiredAt;
}

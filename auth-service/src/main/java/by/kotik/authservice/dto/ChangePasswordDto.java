package by.kotik.authservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChangePasswordDto extends PasswordDto {
    private String oldPassword;
}

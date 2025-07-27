package by.kotik.authservice.validator;

import by.kotik.authservice.annotation.PasswordsMatch;
import by.kotik.authservice.dto.PasswordDto;
import by.kotik.authservice.dto.UserRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordsMatch, PasswordDto> {
    @Override
    public boolean isValid(PasswordDto passwordDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return passwordDto.getPassword() != null
                && passwordDto.getPassword().equals(passwordDto.getConfirmPassword());
    }
}

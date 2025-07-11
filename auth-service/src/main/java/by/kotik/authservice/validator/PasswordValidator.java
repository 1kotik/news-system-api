package by.kotik.authservice.validator;

import by.kotik.authservice.annotation.PasswordsMatch;
import by.kotik.authservice.dto.UserRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordsMatch, UserRegistrationDto> {
    @Override
    public boolean isValid(UserRegistrationDto userRegistrationDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return userRegistrationDto.getPassword() != null
                && userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword());
    }
}

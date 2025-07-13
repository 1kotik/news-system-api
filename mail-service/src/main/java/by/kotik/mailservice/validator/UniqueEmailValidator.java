package by.kotik.mailservice.validator;

import by.kotik.mailservice.annotation.UniqueEmail;
import by.kotik.mailservice.client.InternalUserServiceClient;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final InternalUserServiceClient internalUserServiceClient;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !internalUserServiceClient.doesUserExist(email);
    }
}

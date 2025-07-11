package by.kotik.userservice.validator;

import by.kotik.userservice.annotation.UniqueCredentials;
import by.kotik.userservice.dto.UserCreationDto;
import by.kotik.userservice.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCredentialsValidator implements ConstraintValidator<UniqueCredentials, UserCreationDto> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(UserCreationDto userCreationDto, ConstraintValidatorContext constraintValidatorContext) {
        boolean isEmailExists = userRepository.findByEmail(userCreationDto.getEmail()).isPresent();
        boolean isUsernameExists = userRepository.findByUsername(userCreationDto.getUsername()).isPresent();

        return !isEmailExists && !isUsernameExists;
    }
}

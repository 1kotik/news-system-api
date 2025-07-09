package by.kotik.mailservice.mapper;

import by.kotik.mailservice.dto.ConfirmationCodeDto;
import by.kotik.mailservice.entity.RegistrationConfirmationCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfirmationCodeMapper {
    ConfirmationCodeDto RegistrationConfirmationCodeToDto
            (RegistrationConfirmationCode registrationConfirmationCode);
}

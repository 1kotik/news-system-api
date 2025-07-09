package by.kotik.mailservice.service;

import by.kotik.mailservice.dto.ConfirmationCodeDto;
import org.springframework.stereotype.Service;

@Service
public interface UserMailService {
    void generateConfirmationCode(String email);
    ConfirmationCodeDto checkConfirmationCode(ConfirmationCodeDto confirmationCodeDto);
}

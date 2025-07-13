package by.kotik.mailservice.service;

import by.kotik.mailservice.dto.ConfirmationCodeDto;
import dto.TokenDto;
import org.springframework.stereotype.Service;

@Service
public interface UserMailService {
    TokenDto generateConfirmationCode(String email);
    TokenDto checkConfirmationCode(ConfirmationCodeDto confirmationCodeDto);
}

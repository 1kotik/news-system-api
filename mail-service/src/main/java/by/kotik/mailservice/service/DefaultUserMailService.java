package by.kotik.mailservice.service;

import by.kotik.mailservice.dto.ConfirmationCodeDto;
import by.kotik.mailservice.entity.RegistrationConfirmationCode;
import by.kotik.mailservice.exception.InvalidConfirmationCode;
import by.kotik.mailservice.mapper.ConfirmationCodeMapper;
import by.kotik.mailservice.repository.RegistrationConfirmationCodeRepository;
import dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.JwtUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DefaultUserMailService implements UserMailService {
    private final JavaMailSender mailSender;
    private final RegistrationConfirmationCodeRepository registrationConfirmationCodeRepository;
    private final ConfirmationCodeMapper confirmationCodeMapper;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public TokenDto generateConfirmationCode(String email) {
        int code = generateCode();

        sendRegistrationConfirmationCodeEmail(email, code);

        return jwtUtils.insertAuthorities(null, List.of(email, String.valueOf(code)),
                Duration.ofMinutes(60));
    }

    @Override
    @Transactional
    public TokenDto checkConfirmationCode(ConfirmationCodeDto confirmationCodeDto) {
        return jwtUtils.insertAuthorities(null, List.of(confirmationCodeDto.getEmail()),
                Duration.ofMinutes(60));
    }

    private int generateCode() {
        return 100000 + new Random().nextInt(899999);
    }

    private void sendRegistrationConfirmationCodeEmail(String email, int code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Registration Confirmation Code");
        mailMessage.setText("Your confirmation code is " + code);

        mailSender.send(mailMessage);
    }
}

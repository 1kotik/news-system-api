package by.kotik.mailservice.service;

import by.kotik.mailservice.dto.ConfirmationCodeDto;
import by.kotik.mailservice.entity.RegistrationConfirmationCode;
import by.kotik.mailservice.mapper.ConfirmationCodeMapper;
import by.kotik.mailservice.repository.RegistrationConfirmationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DefaultUserMailService implements UserMailService {
    private final JavaMailSender mailSender;
    private final RegistrationConfirmationCodeRepository registrationConfirmationCodeRepository;
    private final ConfirmationCodeMapper confirmationCodeMapper;

    @Override
    @Transactional
    public void generateConfirmationCode(String email) {
        int code = generateCode();

        RegistrationConfirmationCode confirmationCode = registrationConfirmationCodeRepository
                .save(RegistrationConfirmationCode.builder()
                        .code(code)
                        .email(email)
                        .expiredAt(ZonedDateTime.now().plusHours(4))
                        .build());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Registration confirmation Code");
        mailMessage.setText("Your confirmation code is " + code);

        mailSender.send(mailMessage);
    }

    @Override
    @Transactional
    public ConfirmationCodeDto checkConfirmationCode(ConfirmationCodeDto confirmationCodeDto) {
        RegistrationConfirmationCode confirmationCode = registrationConfirmationCodeRepository
                .findByEmail(confirmationCodeDto.getEmail())
                .orElseThrow(() -> new NoSuchElementException("Registration confirmation code not found"));

        if (confirmationCode.getCode() != confirmationCodeDto.getCode()) {
            throw new RuntimeException("Invalid confirmation code");
        }

        if(confirmationCode.getExpiredAt().isBefore(ZonedDateTime.now())) {
            registrationConfirmationCodeRepository.delete(confirmationCode);
            throw new RuntimeException("Expired confirmation code");
        }

        registrationConfirmationCodeRepository.delete(confirmationCode);

        return confirmationCodeDto;
    }

    private int generateCode() {
        return 100000 + new Random().nextInt(899999);
    }
}

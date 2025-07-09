package by.kotik.mailservice.repository;

import by.kotik.mailservice.entity.RegistrationConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationConfirmationCodeRepository
        extends JpaRepository<RegistrationConfirmationCode, UUID> {
    Optional<RegistrationConfirmationCode> findByEmail(String email);
}

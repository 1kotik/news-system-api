package by.kotik.mailservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "register_email_confirmation_codes")
@Builder
@AllArgsConstructor
public class RegistrationConfirmationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "code_id", nullable = false)
    private UUID codeId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "code", nullable = false)
    private int code;

    @Column(name = "expired_at", nullable = false)
    private ZonedDateTime expiredAt;
}

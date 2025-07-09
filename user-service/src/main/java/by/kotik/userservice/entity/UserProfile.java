package by.kotik.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "user_profiles")
@RequiredArgsConstructor
@Getter
@Setter
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "profile_id", nullable = false)
    private UUID profileId;
    @Column(name = "public_name")
    private String publicName;
    @Column(name = "bio")
    private String bio;
    @Column(name = "location")
    private String location;
    @Column(name = "avatar")
    private String avatar;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}

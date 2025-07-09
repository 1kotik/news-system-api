package by.kotik.userservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserProfileDto {
    private UUID profileId;
    private String publicName;
    private String bio;
    private String location;
    private String avatar;
    private UUID userId;
}

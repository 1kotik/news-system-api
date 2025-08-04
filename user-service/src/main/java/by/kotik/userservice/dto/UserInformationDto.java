package by.kotik.userservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UserInformationDto {
    private UUID userId;
    private List<String> roles = new ArrayList<>();
    private String username;
    private String email;
    private UserProfileDto userProfile;
}

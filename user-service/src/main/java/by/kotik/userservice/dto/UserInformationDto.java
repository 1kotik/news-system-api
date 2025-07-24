package by.kotik.userservice.dto;

import lombok.Data;

@Data
public class UserInformationDto {
    private String username;
    private String email;
    private UserProfileDto userProfile;
}

package by.kotik.userservice.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;
    private String email;
    private String password;
    private String nickname;
    private UserProfileDto userProfile;
    private Set<RoleDto> roles = new HashSet<>();
}

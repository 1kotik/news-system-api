package dto;

import lombok.Data;

@Data
public class UserRegistrationTransitiveDto {
    private String username;
    private String password;
    private String email;
}

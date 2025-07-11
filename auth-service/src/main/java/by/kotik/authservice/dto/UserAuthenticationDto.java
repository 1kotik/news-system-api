package by.kotik.authservice.dto;

import lombok.Data;

@Data
public class UserAuthenticationDto {
    private String login;
    private String password;
}

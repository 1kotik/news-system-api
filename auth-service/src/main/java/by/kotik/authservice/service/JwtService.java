package by.kotik.authservice.service;

import dto.TokenDto;
import dto.UserAuthorizationDto;

public interface JwtService {
    UserAuthorizationDto validateToken(String token);
    TokenDto generateToken(UserAuthorizationDto userAuthorizationDto);
}

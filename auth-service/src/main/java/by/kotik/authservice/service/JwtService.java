package by.kotik.authservice.service;

import dto.UserAuthorizationDto;

public interface JwtService {
    UserAuthorizationDto validateToken(String token);
    String generateToken(UserAuthorizationDto userAuthorizationDto);
}

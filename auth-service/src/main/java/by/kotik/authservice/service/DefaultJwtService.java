package by.kotik.authservice.service;

import dto.TokenDto;
import dto.UserAuthorizationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import util.JwtUtils;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class DefaultJwtService implements JwtService {
    @Value("${jwt.lifetime}")
    private Duration lifetime;
    private final JwtUtils jwtUtils;

    @Override
    public UserAuthorizationDto validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    @Override
    public TokenDto generateToken(UserAuthorizationDto userAuthorizationDto) {
        return jwtUtils.generateAuthenticationToken(userAuthorizationDto, lifetime);
    }
}

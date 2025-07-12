package by.kotik.authservice.service;

import dto.UserAuthorizationDto;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import util.JwtUtils;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    public String generateToken(UserAuthorizationDto userAuthorizationDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userAuthorizationDto.getUserId().toString());
        claims.put("username", userAuthorizationDto.getUsername());
        claims.put("email", userAuthorizationDto.getEmail());
        claims.put("roles", userAuthorizationDto.getRoles());

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + lifetime.toMillis());

        return Jwts.builder()
                .claims(claims)
                .subject(userAuthorizationDto.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .signWith(jwtUtils.getKey())
                .compact();
    }
}

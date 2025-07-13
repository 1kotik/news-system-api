package util;

import dto.TokenDto;
import dto.UserAuthorizationDto;
import exception.GenericAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class JwtUtils {
    private String secret;

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UserAuthorizationDto validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return new UserAuthorizationDto(UUID.fromString(claims.get("id", String.class)),
                    claims.get("username", String.class),
                    claims.get("email", String.class),
                    claims.get("roles", List.class));
        } catch (Exception e) {
            return new UserAuthorizationDto();
        }
    }

    public SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public List<String> extractAuthorities(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.get("authorities", List.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public TokenDto insertAuthorities(String authorityHeader, List<String> authorities, Duration duration) {
        List<String> existingAuthorities = authorityHeader != null
                ? extractAuthorities(authorityHeader) : new ArrayList<>();
        existingAuthorities.addAll(authorities);

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities);

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + duration.toMillis());

        return new TokenDto(Jwts.builder()
                .claims(claims)
                .subject("authorities")
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .signWith(getKey())
                .compact());
    }

    public TokenDto generateAuthenticationToken(UserAuthorizationDto userAuthorizationDto, Duration lifetime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userAuthorizationDto.getUserId().toString());
        claims.put("username", userAuthorizationDto.getUsername());
        claims.put("email", userAuthorizationDto.getEmail());
        claims.put("roles", userAuthorizationDto.getRoles());

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + lifetime.toMillis());

        return new TokenDto(Jwts.builder()
                .claims(claims)
                .subject(userAuthorizationDto.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .signWith(getKey())
                .compact());
    }

}

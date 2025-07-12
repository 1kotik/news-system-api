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
            throw new GenericAuthenticationException("Invalid JWT");
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
            throw new GenericAuthenticationException("Invalid Authorities JWT");
        }
    }

    public TokenDto insertAuthority(String authorityHeader, String authority) {
        List<String> authorities = authorityHeader != null
                ? extractAuthorities(authorityHeader) : new ArrayList<>();
        authorities.add(authority);

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities);

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + Duration.ofMinutes(5).toMillis());

        return new TokenDto(Jwts.builder()
                .claims(claims)
                .subject("authorities")
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .signWith(getKey())
                .compact());
    }

}

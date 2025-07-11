package util;

import dto.UserAuthorizationDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class JwtUtils {
    private JwtUtils(){}
    public static UserAuthorizationDto validateToken(String token, String secret) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return new UserAuthorizationDto(UUID.fromString(claims.get("id", String.class)),
                    claims.get("username", String.class),
                    claims.get("email", String.class),
                    claims.get("roles", List.class));
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT");
        }
    }

    public static SecretKey getKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}

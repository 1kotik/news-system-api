package by.kotik.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import util.JwtUtils;

@Configuration
public class BeanConfiguration {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(secret);
    }
}

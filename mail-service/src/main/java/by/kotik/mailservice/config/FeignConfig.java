package by.kotik.mailservice.config;

import dto.TokenDto;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import util.JwtUtils;

import java.time.Duration;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private final JwtUtils jwtUtils;
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            TokenDto token = jwtUtils.insertAuthorities(null, List.of("MAIL_SERVICE"),
                    Duration.ofMinutes(5));
            requestTemplate.header("X-Authorities", token.getToken());
        };
    }
}

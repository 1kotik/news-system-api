package by.kotik.authservice.config;

import dto.TokenDto;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import util.JwtUtils;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private final JwtUtils jwtUtils;
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            TokenDto token = jwtUtils.insertAuthority(null, "AUTH_SERVICE");
            requestTemplate.header("X-Authorities", token.getToken());
        };
    }
}

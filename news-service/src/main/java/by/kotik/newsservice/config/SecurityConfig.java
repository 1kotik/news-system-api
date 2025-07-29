package by.kotik.newsservice.config;

import dto.UserAuthorizationDto;
import filter.UserInfoExtractionFilter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.security.auth.AuthenticationContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.annotation.RequestScope;
import util.JwtUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new UserInfoExtractionFilter(jwtUtils()), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(secret);
    }

    @Bean
    @RequestScope
    UserAuthorizationDto userAuthorization() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserAuthorizationDto) authentication.getDetails();
    }
}

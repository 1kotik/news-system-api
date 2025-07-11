package by.kotik.authservice.service;

import by.kotik.authservice.dto.CustomUserDetails;
import by.kotik.authservice.dto.UserAuthenticationDto;
import by.kotik.authservice.dto.UserRegistrationDto;
import by.kotik.authservice.mapper.UserMapper;
import dto.UserAuthorizationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public String login(UserAuthenticationDto userAuthenticationDto) {
        CustomUserDetails userDetails;
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userAuthenticationDto.getLogin(),
                                                                          userAuthenticationDto.getPassword()));
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        } catch (Exception e) {
            throw new RuntimeException("Invalid Credentials.");
        }
        return jwtService.generateToken(userMapper.customUserDetailsDtoToUserAuthorizationDto(userDetails));
    }

    @Override
    public String register(UserRegistrationDto userRegistrationDto) {
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        UserAuthorizationDto createdUser = userDetailsService.createUser(userMapper.toTransitiveDto(userRegistrationDto));
        return jwtService.generateToken(createdUser);
    }
}

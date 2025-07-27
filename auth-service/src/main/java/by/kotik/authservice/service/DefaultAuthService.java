package by.kotik.authservice.service;

import by.kotik.authservice.dto.ChangePasswordDto;
import by.kotik.authservice.dto.CustomUserDetails;
import by.kotik.authservice.dto.UserRegistrationDto;
import by.kotik.authservice.mapper.UserMapper;
import dto.TokenDto;
import dto.UserAuthenticationDto;
import dto.UserAuthorizationDto;
import exception.GenericAuthenticationException;
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
    public TokenDto login(UserAuthenticationDto userAuthenticationDto) {
        CustomUserDetails userDetails = authenticate(userAuthenticationDto.getLogin(),
                                                        userAuthenticationDto.getPassword());
        return jwtService.generateToken(userMapper.customUserDetailsDtoToUserAuthorizationDto(userDetails));
    }

    @Override
    public TokenDto register(UserRegistrationDto userRegistrationDto) {
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        UserAuthorizationDto createdUser = userDetailsService.createUser(userMapper.toTransitiveDto(userRegistrationDto));
        return jwtService.generateToken(createdUser);
    }

    @Override
    public TokenDto changePassword(ChangePasswordDto changePasswordDto, UserAuthorizationDto userAuthorizationDto) {
        authenticate(userAuthorizationDto.getEmail(), changePasswordDto.getOldPassword());
        String newPassword = passwordEncoder.encode(changePasswordDto.getPassword());

        UserAuthorizationDto updatedUser = userDetailsService
                .changePassword(userAuthorizationDto.getEmail(), newPassword);

        return jwtService.generateToken(updatedUser);
    }

    private CustomUserDetails authenticate(String login, String password) {
        CustomUserDetails userDetails;
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(login, password));
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        } catch (Exception e) {
            throw new GenericAuthenticationException("Invalid Credentials.");
        }
        return userDetails;
    }

}

package by.kotik.authservice.service;

import by.kotik.authservice.client.InternalUserServiceClient;
import by.kotik.authservice.mapper.UserMapper;
import dto.UserAuthorizationDto;
import dto.UserDetailsDto;
import dto.UserRegistrationTransitiveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultCustomUserDetailsService implements CustomUserDetailsService {
    private final InternalUserServiceClient internalUserServiceClient;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsDto userDetailsDto = internalUserServiceClient.getUserDetailsByLogin(username);
        return Optional.ofNullable(userDetailsDto).map(userMapper::userDetailsDtoToCustomUserDetails)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserAuthorizationDto createUser(UserRegistrationTransitiveDto registrationTransitiveDto) {
        return internalUserServiceClient.createUser(registrationTransitiveDto);
    }
}

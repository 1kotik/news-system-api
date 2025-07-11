package by.kotik.authservice.client;

import dto.UserAuthorizationDto;
import dto.UserDetailsDto;
import dto.UserRegistrationTransitiveDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("user-service")
public interface InternalUserServiceClient {
    @GetMapping("/users/internal/{login}")
    UserDetailsDto getUserDetailsByLogin(@PathVariable String login);
    @PostMapping("/users/internal")
    UserAuthorizationDto createUser(@RequestBody UserRegistrationTransitiveDto registrationDto);
}

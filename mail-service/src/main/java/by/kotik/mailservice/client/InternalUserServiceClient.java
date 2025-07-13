package by.kotik.mailservice.client;

import by.kotik.mailservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface InternalUserServiceClient {
    @GetMapping("/users/internal/email/{email}")
    boolean doesUserExist(@PathVariable String email);
}

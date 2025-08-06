package by.kotik.newsservice.client;

import dto.UserPreviewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@FeignClient(name = "user-service")
public interface InternalUserServiceClient {
    @GetMapping("/users/internal/previews")
    Map<UUID, UserPreviewDto> getUserPreviews(@RequestParam(name = "userId", required = false) Set<UUID> userIds);
}

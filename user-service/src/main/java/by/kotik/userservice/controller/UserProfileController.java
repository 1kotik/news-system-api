package by.kotik.userservice.controller;

import by.kotik.userservice.dto.UserProfileDto;
import by.kotik.userservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable UUID userId) {
        return ResponseEntity.ok(userProfileService.findUserProfileByUserId(userId));
    }

    @PreAuthorize("hasRole('ADMIN') or #userId eq principal")
    @PutMapping("/{userId}/profile")
    public ResponseEntity<UserProfileDto> updateUserProfile(@PathVariable UUID userId,
                                                            @RequestBody UserProfileDto userProfileDto) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(userId, userProfileDto));
    }
}

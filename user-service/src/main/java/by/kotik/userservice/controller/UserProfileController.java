package by.kotik.userservice.controller;

import by.kotik.userservice.dto.UserProfileDto;
import by.kotik.userservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping("/{login}/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String login) {
        return ResponseEntity.ok(userProfileService.findUserProfileByLogin(login));
    }

    @PreAuthorize("hasRole('ADMIN') or #login eq authentication.details.username" +
            " or #login eq authentication.details.email")
    @PutMapping("/{login}/profile")
    public ResponseEntity<UserProfileDto> updateUserProfile(@PathVariable String login,
                                                            @RequestBody UserProfileDto userProfileDto) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(login, userProfileDto));
    }

    @PreAuthorize("hasRole('ADMIN') or #login eq authentication.details.username" +
            " or #login eq authentication.details.email")
    @PostMapping(value = "/{login}/profile/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileDto> updateAvatar(@PathVariable String login,
                                                       @RequestPart(name = "file") MultipartFile file) {
        return ResponseEntity.ok(userProfileService.setAvatar(file, login));
    }
}

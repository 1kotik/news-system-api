package by.kotik.userservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String uploadUserAvatar(MultipartFile file, String login);
}

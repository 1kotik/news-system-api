package by.kotik.userservice.service;

import exception.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service("localFileStorageService")
public class LocalFileStorageService implements FileStorageService {
    @Value("${file-storage.local.service.path}")
    private String STORAGE_PATH;

    @Override
    public String uploadUserAvatar(MultipartFile file, String login) {
        String fileExtension = getFileExtension(file);
        String relativePath = "/avatar/" + login + fileExtension;
        File avatarFile = new File(STORAGE_PATH + relativePath);

        try (FileOutputStream fos = new FileOutputStream(avatarFile)) {
            fos.write(file.getBytes());
        } catch (FileNotFoundException e) {
            throw new AppException(500, "Local File Service Error", "File Not Found");
        } catch (IOException e) {
            throw new AppException(500, "Local File Service Error", e.getMessage());
        }

        return "/resources" + relativePath;
    }

    private String getFileExtension(MultipartFile file) {
        String fileExtension;
        try {
            fileExtension = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf("."));
        } catch (Exception e) {
            fileExtension = ".png";
        }
        return fileExtension;
    }
}

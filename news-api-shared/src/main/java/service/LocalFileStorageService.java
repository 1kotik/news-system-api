package service;

import exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
public class LocalFileStorageService implements FileStorageService {
    private final String STORAGE_PATH;

    @Override
    public String uploadFile(MultipartFile file, String directory, String id) {
        String fileExtension = getFileExtension(file);
        directory = appendSlashToDirectory(directory);
        String relativePath = directory + id + fileExtension;
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

    private String appendSlashToDirectory(String directory) {
        String separator = "/";
        StringBuilder sb = new StringBuilder(directory);
        if (!directory.startsWith(separator)) {
            sb.insert(0, separator);
        }
        if (!directory.endsWith(separator)) {
            sb.append(separator);
        }
        return sb.toString();
    }
}

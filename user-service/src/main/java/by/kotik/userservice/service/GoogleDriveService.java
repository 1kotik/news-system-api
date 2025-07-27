package by.kotik.userservice.service;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleDriveService implements FileStorageService {
    private final Drive drive;
    private final String AVATAR_FOLDER_ID = "1evagQpJy5WfrXFOJ61iW26L3ZaNLC_Zf";

    @Override
    public String uploadUserAvatar(MultipartFile file, String login) {
        File metadata = new File();
        metadata.setName("avatar" + UUID.randomUUID());
        metadata.setParents(Collections.singletonList(AVATAR_FOLDER_ID));

        File fileToUpload;
        try {
            InputStreamContent content = new InputStreamContent(file.getContentType(), file.getInputStream());

            fileToUpload = drive.files()
                    .create(metadata, content)
                    .setFields("id, webViewLink")
                    .execute();

            drive.permissions()
                    .create(fileToUpload.getId(), new Permission()
                            .setType("anyone")
                            .setRole("reader"))
                    .execute();
        } catch (IOException e) {
            throw new AppException(500, "Google Service Error", e.getMessage());
        }

        return fileToUpload.getWebViewLink();
    }
}

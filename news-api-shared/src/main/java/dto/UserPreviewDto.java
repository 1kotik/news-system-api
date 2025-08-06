package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreviewDto {
    private UUID userId;
    private String displayedName;
    private String avatarUrl;
}

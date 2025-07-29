package by.kotik.newsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    private UUID likeId;
    private UUID userId;
    private UUID newsId;
    private String likeType;
}

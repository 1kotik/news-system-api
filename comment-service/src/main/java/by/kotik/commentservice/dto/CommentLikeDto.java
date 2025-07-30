package by.kotik.commentservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentLikeDto {
    private UUID likeId;
    private UUID userId;
    private String likeType;
    private UUID commentId;
}

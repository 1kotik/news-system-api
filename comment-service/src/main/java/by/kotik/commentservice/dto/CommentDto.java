package by.kotik.commentservice.dto;

import dto.UserPreviewDto;
import enums.LikeType;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CommentDto {
    private UUID commentId;
    private UUID newsId;
    private UUID userId;
    private String content;
    private ZonedDateTime createdAt;
    private int likesCount;
    private int dislikesCount;
    private List<CommentDto> childComments = new ArrayList<>();
    private LikeType currentUserLike;
    private UserPreviewDto authorPreview;
}

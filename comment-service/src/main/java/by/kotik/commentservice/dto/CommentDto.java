package by.kotik.commentservice.dto;

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
    private List<CommentLikeDto> commentLikes = new ArrayList<>();
}

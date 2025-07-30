package by.kotik.newsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
    private UUID newsId;
    private UUID authorId;
    private String title;
    private String content;
    private String previewImageUrl;
    private ZonedDateTime createdAt;
    private int likesCount;
    private int dislikesCount;
    private int commentsCount;
    private List<CategoryDto> categories = new ArrayList<>();
    private List<NewsLikeDto> likes = new ArrayList<>();
}

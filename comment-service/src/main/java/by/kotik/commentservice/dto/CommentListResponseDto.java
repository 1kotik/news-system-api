package by.kotik.commentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponseDto {
    private UUID newsId;
    private List<CommentDto> comments = new ArrayList<>();
    private int offset;
    private int limit;
    private int total;
}

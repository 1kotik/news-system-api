package by.kotik.newsservice.dto;

import dto.NewsPreviewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsListResponseDto {
    private List<NewsPreviewDto> newsPreviewDtos = new ArrayList<>();
    private int offset;
    private int limit;
    private int total;
}

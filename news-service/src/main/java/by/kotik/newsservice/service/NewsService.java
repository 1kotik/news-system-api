package by.kotik.newsservice.service;

import by.kotik.newsservice.dto.NewsContentDto;
import by.kotik.newsservice.dto.NewsDto;
import by.kotik.newsservice.entity.News;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    List<NewsDto> findAll();
    NewsDto createNews(NewsContentDto newsContentDto, List<UUID> categoryIds, MultipartFile previewImage);
    NewsDto updateNews(NewsContentDto newsContentDto, UUID newsId, List<UUID> categoryIds, MultipartFile previewImage);
    void deleteNews(UUID newsId);
    News findById(UUID newsId);
    void changeCommentCount(UUID newsId, boolean increment);
    NewsDto findDtoById(UUID newsId);
}

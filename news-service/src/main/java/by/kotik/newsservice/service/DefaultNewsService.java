package by.kotik.newsservice.service;

import by.kotik.newsservice.client.InternalUserServiceClient;
import by.kotik.newsservice.dto.NewsContentDto;
import by.kotik.newsservice.dto.NewsDto;
import by.kotik.newsservice.dto.NewsListResponseDto;
import by.kotik.newsservice.entity.Category;
import by.kotik.newsservice.entity.News;
import by.kotik.newsservice.exception.NewsNotFoundException;
import by.kotik.newsservice.exception.UnauthorizedNewsModifyingException;
import by.kotik.newsservice.mapper.NewsMapper;
import by.kotik.newsservice.repository.NewsRepository;
import dto.NewsPreviewDto;
import dto.UserAuthorizationDto;
import dto.UserPreviewDto;
import event.NewsDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import service.FileStorageService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultNewsService implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final UserAuthorizationDto userAuthorizationDto;
    private final CategoryService categoryService;
    private final FileStorageService fileStorageService;
    private final KafkaTemplate<UUID, Object> newsDeletedEventKafkaTemplate;
    private final InternalUserServiceClient internalUserServiceClient;

    @Value("${file-storage.local.service.preview-image-folder-name}")
    private String previewImageFolderName;
    @Value("${kafka.topic.news-deleted-topic-name}")
    private String newsDeletedTopicName;

    @Override
    @Transactional(readOnly = true)
    public NewsListResponseDto findByCategories(List<UUID> categoryIds, int offset, int limit) {
        List<Category> categories = categoryService.findByCategoryIds(categoryIds);
        List<News> news = categories.isEmpty() ? newsRepository.findAllSortedByCreatedAtDesc()
                : newsRepository.findByCategories(categories);

        List<NewsPreviewDto> filteredNews = news.stream()
                .skip(offset)
                .limit(limit)
                .map(newsMapper::toNewsPreviewDto)
                .toList();

        return new NewsListResponseDto(filteredNews, offset, limit, news.size());
    }

    @Override
    @Transactional
    public NewsDto createNews(NewsContentDto newsContentDto, List<UUID> categoryIds, MultipartFile previewImage) {
        News news = newsMapper.fromContentToEntity(newsContentDto);
        UUID userId = userAuthorizationDto.getUserId();
        List<Category> categories = categoryService.findByCategoryIds(categoryIds);

        if (previewImage != null) {
            String previewImageUrl = fileStorageService.uploadFile(previewImage,
                    previewImageFolderName,
                    UUID.randomUUID().toString());
            news.setPreviewImageUrl(previewImageUrl);
        }

        news.setAuthorId(userId);
        news.setCategories(categories);

        newsRepository.save(news);

        return newsMapper.toDto(news, userAuthorizationDto, internalUserServiceClient);
    }

    @Override
    @Transactional
    public NewsDto updateNews(NewsContentDto newsContentDto, UUID newsId,
                              List<UUID> categoryIds, MultipartFile previewImage) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(newsId));
        List<Category> categories = categoryService.findByCategoryIds(categoryIds);

        checkIfUnauthorizedNewsModifying(news);

        if (previewImage != null) {
            String previewImageUrl = fileStorageService.uploadFile(previewImage,
                    previewImageFolderName,
                    news.getNewsId().toString());
            news.setPreviewImageUrl(previewImageUrl);
        }

        newsMapper.updateEntity(newsContentDto, news);
        news.setCategories(categories);
        News updatedNews = newsRepository.save(news);

        return newsMapper.toDto(updatedNews, userAuthorizationDto, internalUserServiceClient);
    }

    @Override
    @Transactional
    public void deleteNews(UUID newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(newsId));

        checkIfUnauthorizedNewsModifying(news);

        newsRepository.delete(news);

        newsDeletedEventKafkaTemplate.send(newsDeletedTopicName, newsId,
                new NewsDeletedEvent(newsId));
    }

    @Override
    @Transactional(readOnly = true)
    public News findById(UUID newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(newsId));
    }

    @Override
    @Transactional
    public void changeCommentCount(UUID newsId, boolean increment) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(newsId));

        int commentsCount = news.getCommentsCount();

        if (increment) {
            commentsCount++;
        } else {
            commentsCount--;
        }

        news.setCommentsCount(commentsCount);

        newsRepository.save(news);
    }

    @Override
    public NewsDto findDtoById(UUID newsId) {
        return newsRepository.findById(newsId)
                .map(news -> newsMapper.toDto(news, userAuthorizationDto, internalUserServiceClient))
                .orElseThrow(() -> new NewsNotFoundException(newsId));
    }

    @Override
    public NewsPreviewDto findPreviewDtoById(UUID newsId) {
        return newsRepository.findById(newsId)
                .map(newsMapper::toNewsPreviewDto)
                .orElseThrow(() -> new NewsNotFoundException(newsId));
    }

    private void checkIfUnauthorizedNewsModifying(News news) {
        boolean isAuthorized = news.getAuthorId().equals(userAuthorizationDto.getUserId())
                || userAuthorizationDto.getRoles().contains("ROLE_ADMIN");
        if (!isAuthorized) {
            throw new UnauthorizedNewsModifyingException(news.getNewsId(), userAuthorizationDto.getUserId());
        }
    }
}

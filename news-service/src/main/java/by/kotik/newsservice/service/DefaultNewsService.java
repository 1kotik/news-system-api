package by.kotik.newsservice.service;

import by.kotik.newsservice.dto.NewsContentDto;
import by.kotik.newsservice.dto.NewsDto;
import by.kotik.newsservice.entity.Category;
import by.kotik.newsservice.entity.News;
import by.kotik.newsservice.exception.NewsNotFoundException;
import by.kotik.newsservice.exception.UnauthorizedNewsModifyingException;
import by.kotik.newsservice.mapper.NewsMapper;
import by.kotik.newsservice.repository.NewsRepository;
import dto.UserAuthorizationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import service.FileStorageService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultNewsService implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final UserAuthorizationDto userAuthorizationDto;
    private final CategoryService categoryService;
    private final FileStorageService fileStorageService;
    @Value("${file-storage.local.service.preview-image-folder-name}")
    private String previewImageFolderName;

    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> findAll() {
        return newsRepository.findAll()
                .stream()
                .map(newsMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public NewsDto createNews(NewsContentDto newsContentDto, List<UUID> categoryIds, MultipartFile previewImage) {
        News news = newsMapper.fromContentToEntity(newsContentDto);
        UUID userId = userAuthorizationDto.getUserId();
        List<Category> categories = categoryService.findByCategoryIds(categoryIds);

        if(previewImage != null) {
            String previewImageUrl = fileStorageService.uploadFile(previewImage,
                    previewImageFolderName,
                    UUID.randomUUID().toString());
            news.setPreviewImageUrl(previewImageUrl);
        }

        news.setAuthorId(userId);
        news.setCategories(categories);

        newsRepository.save(news);

        return newsMapper.toDto(news);
    }

    @Override
    @Transactional
    public NewsDto updateNews(NewsContentDto newsContentDto, UUID newsId,
                              List<UUID> categoryIds, MultipartFile previewImage) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(newsId));
        UUID userId = userAuthorizationDto.getUserId();
        List<Category> categories = categoryService.findByCategoryIds(categoryIds);

        if(!news.getAuthorId().equals(userId)) {
            throw new UnauthorizedNewsModifyingException(newsId, userId);
        }

        if(previewImage != null) {
            String previewImageUrl = fileStorageService.uploadFile(previewImage,
                    previewImageFolderName,
                    news.getNewsId().toString());
            news.setPreviewImageUrl(previewImageUrl);
        }

        newsMapper.updateEntity(newsContentDto, news);
        news.setCategories(categories);
        News updatedNews = newsRepository.save(news);

        return newsMapper.toDto(updatedNews);
    }

    @Override
    @Transactional
    public void deleteNews(UUID newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(newsId));
        UUID userId = userAuthorizationDto.getUserId();

        if(!news.getAuthorId().equals(userId)) {
            throw new UnauthorizedNewsModifyingException(newsId, userId);
        }

        newsRepository.delete(news);
    }
}

package by.kotik.newsservice.controller;

import by.kotik.newsservice.dto.NewsContentDto;
import by.kotik.newsservice.dto.NewsDto;
import by.kotik.newsservice.dto.NewsListResponseDto;
import by.kotik.newsservice.service.NewsService;
import dto.NewsPreviewDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<NewsListResponseDto> findByCategories(
            @RequestParam(name = "categoryId", required = false) List<UUID> categoryIds,
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        NewsListResponseDto newsResponse = newsService.findByCategories(categoryIds, offset, limit);
        return ResponseEntity.ok(newsResponse);
    }

    @PreAuthorize("hasRole('EDITOR') or hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NewsDto> createNews(
            @RequestPart(name = "content") @Valid NewsContentDto newsContentDto,
            @RequestParam(name = "categoryId", required = false) List<UUID> categoryIds,
            @RequestPart(name = "previewImage", required = false) MultipartFile previewImage) {
        NewsDto newsDto = newsService.createNews(newsContentDto, categoryIds, previewImage);
        return ResponseEntity.ok(newsDto);
    }

    @PreAuthorize("hasRole('EDITOR') or hasRole('ADMIN')")
    @PutMapping("/{newsId}")
    public ResponseEntity<NewsDto> updateNews(
            @RequestPart(name = "content") @Valid NewsContentDto newsContentDto,
            @PathVariable UUID newsId,
            @RequestParam(name = "categoryId", required = false) List<UUID> categoryIds,
            @RequestPart(name = "previewImage", required = false) MultipartFile previewImage) {
        NewsDto newsDto = newsService.updateNews(newsContentDto, newsId, categoryIds, previewImage);
        return ResponseEntity.ok(newsDto);
    }

    @PreAuthorize("hasRole('EDITOR') or hasRole('ADMIN')")
    @DeleteMapping("/{newsId}")
    public ResponseEntity<Void> deleteNews(@PathVariable UUID newsId) {
        newsService.deleteNews(newsId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsDto> findById(@PathVariable UUID newsId) {
        NewsDto newsDto = newsService.findDtoById(newsId);
        return ResponseEntity.ok(newsDto);
    }
}

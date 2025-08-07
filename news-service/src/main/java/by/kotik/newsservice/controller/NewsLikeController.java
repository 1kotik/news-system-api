package by.kotik.newsservice.controller;

import by.kotik.newsservice.service.NewsLikeService;
import dto.LikeRequest;
import dto.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news/like")
public class NewsLikeController {
    private final NewsLikeService newsLikeService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{newsId}")
    public ResponseEntity<LikeResponseDto> likeNews(@PathVariable UUID newsId,
                                                    @RequestBody LikeRequest likeRequest) {
        LikeResponseDto likeResponseDto = newsLikeService.likeNews(newsId, likeRequest);
        return ResponseEntity.ok(likeResponseDto);
    }
}

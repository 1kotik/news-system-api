package by.kotik.newsservice.service;

import dto.LikeRequest;
import dto.LikeResponseDto;

import java.util.UUID;

public interface NewsLikeService {
    LikeResponseDto likeNews(UUID newsId, LikeRequest likeRequest);
}

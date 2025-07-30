package by.kotik.newsservice.service;

import dto.LikeRequest;

import java.util.UUID;

public interface NewsLikeService {
    void likeNews(UUID newsId, LikeRequest likeRequest);
}

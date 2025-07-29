package by.kotik.newsservice.service;

import by.kotik.newsservice.dto.LikeRequest;
import by.kotik.newsservice.helpers.enums.LikeType;

import java.util.UUID;

public interface LikeService {
    void likeNews(UUID newsId, LikeRequest likeRequest);
}

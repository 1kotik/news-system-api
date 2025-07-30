package by.kotik.commentservice.service;

import dto.LikeRequest;

import java.util.UUID;

public interface CommentLikeService {
    void likeComment(UUID commentId, LikeRequest likeRequest);
}

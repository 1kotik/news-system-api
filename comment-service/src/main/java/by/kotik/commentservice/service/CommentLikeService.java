package by.kotik.commentservice.service;

import dto.LikeRequest;
import dto.LikeResponseDto;

import java.util.UUID;

public interface CommentLikeService {
    LikeResponseDto likeComment(UUID commentId, LikeRequest likeRequest);
}

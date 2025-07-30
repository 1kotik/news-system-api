package by.kotik.commentservice.service;

import by.kotik.commentservice.dto.CommentContentDto;
import by.kotik.commentservice.dto.CommentDto;
import by.kotik.commentservice.entity.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    List<CommentDto> findAll();
    List<CommentDto> findByNewsId(UUID newsId);
    CommentDto create(CommentContentDto commentContentDto, UUID newsId, UUID parentCommentId);
    CommentDto update(CommentContentDto commentContentDto, UUID commentId);
    void delete(UUID commentId);
    Comment findById(UUID commentId);
}

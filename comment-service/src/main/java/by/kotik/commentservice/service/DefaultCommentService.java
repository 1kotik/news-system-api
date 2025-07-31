package by.kotik.commentservice.service;

import by.kotik.commentservice.dto.CommentContentDto;
import by.kotik.commentservice.dto.CommentDto;
import by.kotik.commentservice.entity.Comment;
import by.kotik.commentservice.exception.CommentNotFoundException;
import by.kotik.commentservice.exception.UnauthorizedCommentModifyingException;
import by.kotik.commentservice.mapper.CommentMapper;
import by.kotik.commentservice.repository.CommentRepository;
import dto.UserAuthorizationDto;
import event.CommentCreatedOrDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserAuthorizationDto userAuthorizationDto;
    private final KafkaTemplate<UUID, Object> kafkaTemplate;

    @Value("${kafka.topic.comment-created-or-deleted-topic-name}")
    private String commentCreatedOrDeletedTopicName;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAll() {
        return commentRepository.findAllParents()
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findByNewsId(UUID newsId) {
        return commentRepository.findByNewsId(newsId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CommentDto create(CommentContentDto commentContentDto, UUID newsId, UUID parentCommentId) {
        Comment comment = commentMapper.toEntity(commentContentDto);
        UUID userId = userAuthorizationDto.getUserId();
        Comment parentComment = null;

        if(parentCommentId != null) {
            parentComment = commentRepository.findByCommentIdAndNewsId(parentCommentId, newsId)
                    .orElse(null);
        }

        comment.setNewsId(newsId);
        comment.setUserId(userId);
        comment.setParentComment(parentComment);

        comment = commentRepository.save(comment);

        kafkaTemplate.send(commentCreatedOrDeletedTopicName, comment.getCommentId(),
                new CommentCreatedOrDeletedEvent(comment.getNewsId(), comment.getCommentId(), true));

        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional
    public CommentDto update(CommentContentDto commentContentDto, UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        checkIfModifyingIsAuthorized(comment);

        commentMapper.updateEntity(commentContentDto, comment);

        commentRepository.save(comment);

        return commentMapper.toDto(comment);

    }

    @Override
    @Transactional
    public void delete(UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        checkIfModifyingIsAuthorized(comment);

        commentRepository.delete(comment);

        kafkaTemplate.send(commentCreatedOrDeletedTopicName, comment.getCommentId(),
                new CommentCreatedOrDeletedEvent(comment.getNewsId(), comment.getCommentId(), false));
    }

    @Override
    @Transactional(readOnly = true)
    public Comment findById(UUID commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    @Override
    @Transactional
    public void deleteByNewsId(UUID newsId) {
        commentRepository.deleteByNewsId(newsId);
    }

    void checkIfModifyingIsAuthorized(Comment comment) {
        boolean isAuthorized = comment.getUserId().equals(userAuthorizationDto.getUserId())
                || userAuthorizationDto.getRoles().contains("ROLE_ADMIN");
        if (!isAuthorized) {
            throw new UnauthorizedCommentModifyingException
                    (userAuthorizationDto.getUserId(), comment.getCommentId());
        }
    }
}

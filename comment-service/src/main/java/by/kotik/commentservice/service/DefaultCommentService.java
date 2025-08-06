package by.kotik.commentservice.service;

import by.kotik.commentservice.client.InternalUserServiceClient;
import by.kotik.commentservice.dto.CommentContentDto;
import by.kotik.commentservice.dto.CommentDto;
import by.kotik.commentservice.dto.CommentListResponseDto;
import by.kotik.commentservice.entity.Comment;
import by.kotik.commentservice.exception.CommentNotFoundException;
import by.kotik.commentservice.exception.UnauthorizedCommentModifyingException;
import by.kotik.commentservice.mapper.CommentMapper;
import by.kotik.commentservice.repository.CommentRepository;
import dto.UserAuthorizationDto;
import dto.UserPreviewDto;
import event.CommentCreatedOrDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserAuthorizationDto userAuthorizationDto;
    private final KafkaTemplate<UUID, Object> kafkaTemplate;
    private final InternalUserServiceClient internalUserServiceClient;

    @Value("${kafka.topic.comment-created-or-deleted-topic-name}")
    private String commentCreatedOrDeletedTopicName;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAll() {
        return commentRepository.findAllParents()
                .stream()
                .map(comment -> commentMapper.toDto(comment, userAuthorizationDto))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CommentListResponseDto findByNewsId(UUID newsId, int offset, int limit) {
        List<Comment> commentEntities = commentRepository.findByNewsId(newsId);
        int total = commentEntities.size();
        List<CommentDto> comments = commentEntities.stream()
                .skip(offset)
                .limit(limit)
                .map(comment -> commentMapper.toDto(comment, userAuthorizationDto))
                .toList();

        setUserPreviews(comments);

        return new CommentListResponseDto(newsId, comments, offset, limit, total);
    }

    @Override
    @Transactional
    public CommentDto create(CommentContentDto commentContentDto, UUID newsId, UUID parentCommentId) {
        Comment comment = commentMapper.toEntity(commentContentDto);
        UUID userId = userAuthorizationDto.getUserId();
        Comment parentComment = null;

        if (parentCommentId != null) {
            parentComment = commentRepository.findByCommentIdAndNewsId(parentCommentId, newsId)
                    .orElse(null);
        }

        comment.setNewsId(newsId);
        comment.setUserId(userId);
        comment.setParentComment(parentComment);

        comment = commentRepository.save(comment);

        kafkaTemplate.send(commentCreatedOrDeletedTopicName, comment.getCommentId(),
                new CommentCreatedOrDeletedEvent(comment.getNewsId(), comment.getCommentId(), true));

        return commentMapper.toDto(comment, userAuthorizationDto);
    }

    @Override
    @Transactional
    public CommentDto update(CommentContentDto commentContentDto, UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        checkIfModifyingIsAuthorized(comment);

        commentMapper.updateEntity(commentContentDto, comment);

        commentRepository.save(comment);

        return commentMapper.toDto(comment, userAuthorizationDto);

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

    private void setUserPreviews(List<CommentDto> comments) {
        Set<UUID> userIds = getUserIdsOfCommentSection(comments, new HashSet<>());

        Map<UUID, UserPreviewDto> userPreviews = internalUserServiceClient.getUserPreviews(userIds);

        attachUserPreviewsToComments(comments, userPreviews);
    }

    private Set<UUID> getUserIdsOfCommentSection(List<CommentDto> comments, Set<UUID> userIds) {
        if (comments.isEmpty()) {
            return userIds;
        }

        comments.stream()
                .map(CommentDto::getUserId)
                .forEach(userIds::add);

        comments.stream()
                .map(comment -> getUserIdsOfCommentSection(comment.getChildComments(), userIds))
                .forEach(userIds::addAll);

        return userIds;
    }

    private void attachUserPreviewsToComments(List<CommentDto> comments, Map<UUID, UserPreviewDto> userPreviews) {
        if (comments.isEmpty()) {
            return;
        }

        comments
                .forEach(comment -> comment
                        .setAuthorPreview(userPreviews
                                .getOrDefault(comment.getUserId(),
                                        new UserPreviewDto(null, "Deleted User", null))));

        comments.forEach(comment -> attachUserPreviewsToComments(comment.getChildComments(), userPreviews));
    }
}

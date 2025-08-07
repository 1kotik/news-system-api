package by.kotik.commentservice.service;

import by.kotik.commentservice.entity.Comment;
import by.kotik.commentservice.entity.CommentLike;
import by.kotik.commentservice.mapper.CommentLikeMapper;
import by.kotik.commentservice.repository.CommentLikeRepository;
import dto.LikeRequest;
import dto.LikeResponseDto;
import dto.UserAuthorizationDto;
import enums.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultCommentLikeService implements CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentLikeMapper commentLikeMapper;
    private final CommentService commentService;
    private final UserAuthorizationDto userAuthorizationDto;


    @Override
    @Transactional
    public LikeResponseDto likeComment(UUID commentId, LikeRequest likeRequest) {
        UUID userId = userAuthorizationDto.getUserId();
        Comment comment = commentService.findById(commentId);
        Optional<CommentLike> likeOptional = commentLikeRepository.getLikeByUserIdAndComment(userId, comment);
        LikeType likeType = likeRequest.getLikeType();
        LikeType returnedLikeType;

        if (likeOptional.isPresent()) {
            returnedLikeType = changeLikeType(likeOptional.get(), likeType, comment);
        } else {
            setNewLike(userId, comment, likeType);
            returnedLikeType = likeType;
        }

        return new LikeResponseDto(
                comment.getCommentId(),
                comment.getLikesCount(),
                comment.getDislikesCount(),
                returnedLikeType
        );
    }

    private LikeType changeLikeType(CommentLike commentLike, LikeType likeType, Comment comment) {
        if (commentLike.getLikeType().equals(likeType)) {
            commentLikeRepository.delete(commentLike);
            changeCommentLikesCount(comment, likeType, -1);
            return null;
        } else {
            LikeType oppositeLikeType = LikeType.getOppositeLikeType(likeType);
            commentLike.setLikeType(likeType);
            changeCommentLikesCount(comment, oppositeLikeType, -1);
            changeCommentLikesCount(comment, likeType, 1);
            return likeType;
        }
    }

    private void changeCommentLikesCount(Comment comment, LikeType likeType, int delta) {
        if (likeType.equals(LikeType.LIKE)) {
            comment.setLikesCount(comment.getLikesCount() + delta);
        } else {
            comment.setDislikesCount(comment.getDislikesCount() + delta);
        }
    }

    private void setNewLike(UUID userId, Comment comment, LikeType likeType) {
        CommentLike commentLike = new CommentLike(userId, comment, likeType);
        commentLikeRepository.save(commentLike);
        changeCommentLikesCount(comment, likeType, 1);
    }
}

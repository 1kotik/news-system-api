package by.kotik.commentservice.repository;

import by.kotik.commentservice.entity.Comment;
import by.kotik.commentservice.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, UUID> {
    Optional<CommentLike> getLikeByUserIdAndComment(UUID userId, Comment comment);
}

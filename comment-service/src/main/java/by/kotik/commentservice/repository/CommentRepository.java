package by.kotik.commentservice.repository;

import by.kotik.commentservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @Query("select c from Comment c where c.newsId = :newsId and c.parentComment is null order by c.createdAt desc")
    List<Comment> findByNewsId(UUID newsId);
    @Query("select c from Comment c where c.parentComment is null")
    List<Comment> findAllParents();
    Optional<Comment> findByCommentIdAndNewsId(UUID commentId, UUID newsId);
    void deleteByNewsId(UUID newsId);
}

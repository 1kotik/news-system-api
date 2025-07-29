package by.kotik.newsservice.repository;

import by.kotik.newsservice.entity.Like;
import by.kotik.newsservice.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
    Optional<Like> getLikeByUserIdAndNews(UUID userId, News news);
}

package by.kotik.newsservice.repository;

import by.kotik.newsservice.entity.NewsLike;
import by.kotik.newsservice.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NewsLikeRepository extends JpaRepository<NewsLike, UUID> {
    Optional<NewsLike> getLikeByUserIdAndNews(UUID userId, News news);
}

package by.kotik.newsservice.repository;

import by.kotik.newsservice.entity.Category;
import by.kotik.newsservice.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {
    @Query("select distinct n from News n" +
            " join n.categories c where c in :categories" +
            " order by n.createdAt desc")
    List<News> findByCategories(List<Category> categories);

    @Query("select n from News n order by n.createdAt desc")
    List<News> findAllSortedByCreatedAtDesc();
}

package by.kotik.newsservice.repository;

import by.kotik.newsservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByCategoryName(String name);
    List<Category> findAllByCategoryIdIn(List<UUID> categoryIds);
}

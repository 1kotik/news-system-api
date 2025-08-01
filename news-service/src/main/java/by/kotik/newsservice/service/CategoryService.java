package by.kotik.newsservice.service;

import by.kotik.newsservice.dto.CategoryContentDto;
import by.kotik.newsservice.entity.Category;
import dto.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDto> findAll();
    CategoryDto createCategory(CategoryContentDto categoryContentDto);
    CategoryDto updateCategory(CategoryContentDto categoryContentDto, UUID categoryId);
    void deleteCategory(UUID categoryId);
    List<Category> findByCategoryIds(List<UUID> categoryIds);
}

package by.kotik.newsservice.service;

import by.kotik.newsservice.dto.CategoryContentDto;
import by.kotik.newsservice.dto.CategoryDto;
import by.kotik.newsservice.entity.Category;
import by.kotik.newsservice.exception.CategoryNotFoundException;
import by.kotik.newsservice.mapper.CategoryMapper;
import by.kotik.newsservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryContentDto categoryContentDto) {
        Category category = categoryMapper.toEntity(categoryContentDto);

        categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryContentDto categoryContentDto, UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        categoryMapper.updateEntity(categoryContentDto, category);

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findByCategoryIds(List<UUID> categoryIds) {
        return categoryRepository.findAllByCategoryIdIn(categoryIds);
    }

}

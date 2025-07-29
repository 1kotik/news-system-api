package by.kotik.newsservice.validator;

import by.kotik.newsservice.annotation.UniqueCategory;
import by.kotik.newsservice.dto.CategoryContentDto;
import by.kotik.newsservice.repository.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, CategoryContentDto> {
    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid(CategoryContentDto categoryContentDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return categoryRepository.findByCategoryName(categoryContentDto.getCategoryName()).isEmpty();
    }
}

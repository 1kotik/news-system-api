package by.kotik.newsservice.mapper;

import by.kotik.newsservice.dto.CategoryContentDto;
import by.kotik.newsservice.entity.Category;
import dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryContentDto categoryContentDto);

    void updateEntity(CategoryContentDto categoryContentDto, @MappingTarget Category category);
}

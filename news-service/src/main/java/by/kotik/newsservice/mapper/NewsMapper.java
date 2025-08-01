package by.kotik.newsservice.mapper;

import by.kotik.newsservice.dto.NewsContentDto;
import by.kotik.newsservice.dto.NewsDto;
import by.kotik.newsservice.entity.News;
import dto.NewsPreviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {NewsLikeMapper.class, CategoryMapper.class})
public interface NewsMapper {
    NewsDto toDto(News news);

    News fromContentToEntity(NewsContentDto newsContentDto);

    void updateEntity(NewsContentDto newsContentDto, @MappingTarget News news);

    NewsPreviewDto toNewsPreviewDto(News news);
}

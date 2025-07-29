package by.kotik.newsservice.mapper;

import by.kotik.newsservice.dto.LikeDto;
import by.kotik.newsservice.entity.Like;
import by.kotik.newsservice.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    @Mapping(source = "news", target = "newsId", qualifiedByName = "getNewsId")
    LikeDto toDto(Like like);

    @Named("getNewsId")
    default UUID getNewsId(News news) {
        return news.getNewsId();
    }
}

package by.kotik.newsservice.mapper;

import by.kotik.newsservice.dto.NewsLikeDto;
import by.kotik.newsservice.entity.NewsLike;
import by.kotik.newsservice.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface NewsLikeMapper {
    @Mapping(source = "news", target = "newsId", qualifiedByName = "getNewsId")
    NewsLikeDto toDto(NewsLike newsLike);

    @Named("getNewsId")
    default UUID getNewsId(News news) {
        return news.getNewsId();
    }
}

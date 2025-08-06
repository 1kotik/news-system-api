package by.kotik.newsservice.mapper;

import by.kotik.newsservice.client.InternalUserServiceClient;
import by.kotik.newsservice.dto.NewsContentDto;
import by.kotik.newsservice.dto.NewsDto;
import by.kotik.newsservice.entity.News;
import by.kotik.newsservice.entity.NewsLike;
import dto.NewsPreviewDto;
import dto.UserAuthorizationDto;
import dto.UserPreviewDto;
import enums.LikeType;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {NewsLikeMapper.class, CategoryMapper.class})
public interface NewsMapper {
    @Mapping(source = "likes", target = "currentUserLike", qualifiedByName = "getCurrentUserLike")
    @Mapping(source = "news", target = "authorPreview", qualifiedByName = "getAuthorPreview")
    NewsDto toDto(News news, @Context UserAuthorizationDto userAuthorizationDto,
                  @Context InternalUserServiceClient internalUserServiceClient);

    News fromContentToEntity(NewsContentDto newsContentDto);

    void updateEntity(NewsContentDto newsContentDto, @MappingTarget News news);

    NewsPreviewDto toNewsPreviewDto(News news);

    @Named("getCurrentUserLike")
    default LikeType getCurrentUserLike(List<NewsLike> likes, @Context UserAuthorizationDto user) {
        UUID userId = user.getUserId();

        if (userId == null) {
            return null;
        }

        return likes.stream()
                .filter(like -> userId.equals(like.getUserId()))
                .findFirst()
                .map(NewsLike::getLikeType)
                .orElse(null);
    }

    @Named("getAuthorPreview")
    default UserPreviewDto getAuthorPreview(News news, @Context InternalUserServiceClient internalUserServiceClient) {
        return internalUserServiceClient
                .getUserPreviews(Set.of(news.getAuthorId()))
                .getOrDefault(news.getAuthorId(),
                        new UserPreviewDto(null, "Deleted User", null));
    }
}

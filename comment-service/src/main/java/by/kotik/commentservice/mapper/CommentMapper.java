package by.kotik.commentservice.mapper;

import by.kotik.commentservice.client.InternalUserServiceClient;
import by.kotik.commentservice.dto.CommentContentDto;
import by.kotik.commentservice.dto.CommentDto;
import by.kotik.commentservice.entity.Comment;
import by.kotik.commentservice.entity.CommentLike;
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

@Mapper(componentModel = "spring", uses = CommentLikeMapper.class)
public interface CommentMapper {
    @Mapping(source = "likes", target = "currentUserLike", qualifiedByName = "getCurrentUserLike")
    CommentDto toDto(Comment comment, @Context UserAuthorizationDto user);
    Comment toEntity(CommentContentDto commentContentDto);
    void updateEntity(CommentContentDto commentContentDto, @MappingTarget Comment comment);

    @Named("getCurrentUserLike")
    default LikeType getCurrentUserLike(List<CommentLike> likes, @Context UserAuthorizationDto user) {
        UUID userId = user.getUserId();

        if(userId == null) {
            return null;
        }

        return likes.stream()
                .filter(like -> userId.equals(like.getUserId()))
                .findFirst()
                .map(CommentLike::getLikeType)
                .orElse(null);
    }
}

package by.kotik.commentservice.mapper;

import by.kotik.commentservice.dto.CommentLikeDto;
import by.kotik.commentservice.entity.Comment;
import by.kotik.commentservice.entity.CommentLike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CommentLikeMapper {
    @Mapping(source = "comment", target = "commentId", qualifiedByName = "getCommentId")
    CommentLikeDto toDto(CommentLike commentLike);

    @Named("getCommentId")
    default UUID getCommentId(Comment comment) {
        return comment != null ? comment.getCommentId() : null;
    }
}

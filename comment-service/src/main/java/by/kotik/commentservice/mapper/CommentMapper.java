package by.kotik.commentservice.mapper;

import by.kotik.commentservice.dto.CommentContentDto;
import by.kotik.commentservice.dto.CommentDto;
import by.kotik.commentservice.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = CommentLikeMapper.class)
public interface CommentMapper {
    CommentDto toDto(Comment comment);
    Comment toEntity(CommentContentDto commentContentDto);
    void updateEntity(CommentContentDto commentContentDto, @MappingTarget Comment comment);
}

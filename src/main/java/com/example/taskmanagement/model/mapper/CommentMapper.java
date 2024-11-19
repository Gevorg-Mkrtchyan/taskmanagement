package com.example.taskmanagement.model.mapper;

import com.example.taskmanagement.model.dto.CommentRequestDto;
import com.example.taskmanagement.model.dto.CommentResponseDto;
import com.example.taskmanagement.model.entity.Comment;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TaskMapper.class})
public interface CommentMapper {

    @Mapping(target = "task.id", source = "taskId")
    Comment toEntity(CommentRequestDto commentRequest);

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "author.id")
    CommentResponseDto toResponse(Comment comment);
}

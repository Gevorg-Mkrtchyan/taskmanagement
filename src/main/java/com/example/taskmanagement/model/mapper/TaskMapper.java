package com.example.taskmanagement.model.mapper;


import com.example.taskmanagement.model.dto.TaskRequestDto;
import com.example.taskmanagement.model.dto.TaskResponseDto;
import com.example.taskmanagement.model.entity.Task;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TaskMapper {

    @Mapping(target = "author.id", source = "authorId")
    @Mapping(target = "assignee.id", source = "assigneeId")
    Task toEntity(TaskRequestDto taskRequest);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    TaskResponseDto toResponse(Task task);
}
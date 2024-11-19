package com.example.taskmanagement.model.mapper;

import com.example.taskmanagement.model.dto.UserRequestDto;
import com.example.taskmanagement.model.dto.UserResponseDto;
import com.example.taskmanagement.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", expression = "java(user.getCreatedAt().toString())")
    UserResponseDto toResponse(User user);

    User toEntity(UserRequestDto userRequestDto);
}
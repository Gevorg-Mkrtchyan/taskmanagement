package com.example.taskmanagement.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponseDto {
    UUID id;
    UUID taskId;
    String content;
    UUID userId;
    String username;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
}

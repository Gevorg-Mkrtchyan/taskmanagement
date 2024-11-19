package com.example.taskmanagement.model.dto;

import com.example.taskmanagement.model.enums.Priority;
import com.example.taskmanagement.model.enums.Status;
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
public class TaskRequestDto {
    String title;
    String description;
    Status status;
    Priority priority;
    UUID assigneeId;
    UUID authorId;
}

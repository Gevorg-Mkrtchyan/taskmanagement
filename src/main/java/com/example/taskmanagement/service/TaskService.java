package com.example.taskmanagement.service;

import com.example.taskmanagement.model.dto.TaskRequestDto;
import com.example.taskmanagement.model.dto.TaskResponseDto;
import com.example.taskmanagement.model.entity.Task;
import com.example.taskmanagement.model.enums.Status;
import com.example.taskmanagement.model.mapper.TaskMapper;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        log.info("Creating task with title: {}", taskRequestDto.getTitle());
        Task task = taskMapper.toEntity(taskRequestDto);
        taskRepository.save(task);
        log.info("Task created with ID: {}", task.getId());
        return taskMapper.toResponse(task);
    }

    public TaskResponseDto getTaskById(UUID taskId) {
        log.info("Fetching task by ID: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    log.error("Task with ID: {} not found", taskId);
                    return new RuntimeException("Task not found");
                });
        log.info("Task found with ID: {}", taskId);
        return taskMapper.toResponse(task);
    }

    public List<TaskResponseDto> getTaskByStatus(Status status) {
        log.info("Fetching tasks by status: {}", status);
        List<Task> tasks = taskRepository.findByStatus(status);
        log.info("Found {} tasks with status: {}", tasks.size(), status);
        return tasks.stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> getAllTasks() {
        log.info("Fetching all tasks");
        List<Task> tasks = taskRepository.findAll();
        log.info("Found {} tasks", tasks.size());
        return tasks.stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> getTasksByAuthor(UUID authorId) {
        log.info("Fetching tasks for author with ID: {}", authorId);
        List<Task> tasks = taskRepository.findByAuthorId(authorId);
        log.info("Found {} tasks for author with ID: {}", tasks.size(), authorId);
        return tasks.stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> getTasksByAssignee(UUID assigneeId) {
        log.info("Fetching tasks for assignee with ID: {}", assigneeId);
        List<Task> tasks = taskRepository.findByAssigneeId(assigneeId);
        log.info("Found {} tasks for assignee with ID: {}", tasks.size(), assigneeId);
        return tasks.stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskResponseDto updateTask(UUID taskId, TaskRequestDto taskRequestDto) {
        log.info("Updating task with ID: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    log.error("Task with ID: {} not found", taskId);
                    return new RuntimeException("Task not found");
                });

        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setStatus(taskRequestDto.getStatus());
        task.setPriority(taskRequestDto.getPriority());
        task.setAssignee(
                userRepository.findById(taskRequestDto.getAssigneeId())
                        .orElseThrow(() -> {
                            log.error("Assignee with ID: {} not found", taskRequestDto.getAssigneeId());
                            return new RuntimeException("Assignee not found");
                        })
        );
        Task updatedTask = taskRepository.save(task);
        log.info("Task with ID: {} updated successfully", taskId);
        return taskMapper.toResponse(updatedTask);
    }

    public void deleteTask(UUID taskId) {
        log.info("Deleting task with ID: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    log.error("Task with ID: {} not found", taskId);
                    return new RuntimeException("Task not found");
                });
        taskRepository.delete(task);
        log.info("Task with ID: {} deleted successfully", taskId);
    }
}

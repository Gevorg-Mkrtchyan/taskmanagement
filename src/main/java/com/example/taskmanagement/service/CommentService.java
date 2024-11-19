package com.example.taskmanagement.service;

import com.example.taskmanagement.model.dto.CommentRequestDto;
import com.example.taskmanagement.model.dto.CommentResponseDto;
import com.example.taskmanagement.model.entity.Comment;
import com.example.taskmanagement.model.entity.Task;
import com.example.taskmanagement.model.entity.User;
import com.example.taskmanagement.model.mapper.CommentMapper;
import com.example.taskmanagement.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentResponseDto addComment(CommentRequestDto commentRequest, UUID userId) {
        log.info("Adding comment for task ID: {} by user ID: {}", commentRequest.getTaskId(), userId);

        Task task = taskRepository.findById(commentRequest.getTaskId())
                .orElseThrow(() -> {
                    log.error("Task with ID: {} not found", commentRequest.getTaskId());
                    return new RuntimeException("Task not found");
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID: {} not found", userId);
                    return new RuntimeException("User not found");
                });

        Comment comment = commentMapper.toEntity(commentRequest);
        comment.setTask(task);
        comment.setAuthor(user);
        commentRepository.save(comment);

        log.info("Comment added with ID: {}", comment.getId());
        return commentMapper.toResponse(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(UUID commentId, String newContent) {
        log.info("Updating comment with ID: {}", commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.error("Comment with ID: {} not found", commentId);
                    return new RuntimeException("Comment not found");
                });

        comment.setContent(newContent);
        Comment updatedComment = commentRepository.save(comment);

        log.info("Comment with ID: {} updated successfully", commentId);
        return commentMapper.toResponse(updatedComment);
    }

    public List<CommentResponseDto> getCommentsByTask(UUID taskId) {
        log.info("Fetching comments for task ID: {}", taskId);

        List<Comment> comments = commentRepository.findByTaskId(taskId);
        log.info("Found {} comments for task ID: {}", comments.size(), taskId);

        return comments.stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponseDto> getCommentsByUser(UUID authorId) {
        log.info("Fetching comments for user ID: {}", authorId);

        List<Comment> comments = commentRepository.findByAuthorId(authorId);
        log.info("Found {} comments for user ID: {}", comments.size(), authorId);

        return comments.stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(UUID commentId) {
        log.info("Deleting comment with ID: {}", commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.error("Comment with ID: {} not found", commentId);
                    return new RuntimeException("Comment not found");
                });

        commentRepository.delete(comment);

        log.info("Comment with ID: {} deleted successfully", commentId);
    }
}

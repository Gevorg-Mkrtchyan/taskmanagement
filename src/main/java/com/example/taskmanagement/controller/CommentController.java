package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.dto.CommentRequestDto;
import com.example.taskmanagement.model.dto.CommentResponseDto;
import com.example.taskmanagement.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/comments" ,"/api/v1/comments"})
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByTask(@PathVariable UUID taskId) {
        return ResponseEntity.ok(commentService.getCommentsByTask(taskId));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByUser(@PathVariable UUID authorId) {
        return ResponseEntity.ok(commentService.getCommentsByUser(authorId));
    }

    @PostMapping
    public  ResponseEntity<CommentResponseDto> addComment(@RequestBody @Valid CommentRequestDto commentRequest) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(commentService.addComment(commentRequest, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> editComment(@PathVariable UUID id, @RequestBody String newContent) {
        return ResponseEntity.ok(commentService.updateComment(id, newContent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}

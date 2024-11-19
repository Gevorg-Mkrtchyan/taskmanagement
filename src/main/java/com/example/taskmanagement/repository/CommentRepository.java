package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.entity.Comment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByTaskId(UUID taskId);

    List<Comment> findByAuthorId(UUID authorId);
}
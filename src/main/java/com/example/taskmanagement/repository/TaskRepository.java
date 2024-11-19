package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.entity.Task;
import com.example.taskmanagement.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByAuthorId(UUID authorId);

    List<Task> findByAssigneeId(UUID assigneeId);

    List<Task> findByStatus(Status status);
}

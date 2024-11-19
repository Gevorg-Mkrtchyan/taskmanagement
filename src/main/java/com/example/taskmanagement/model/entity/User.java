package com.example.taskmanagement.model.entity;

import com.example.taskmanagement.model.enums.Role;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, unique = true)
    String name;

    @CreationTimestamp
    LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    Role role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Task> authoredTasks;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    List<Task> assignedTasks;
}
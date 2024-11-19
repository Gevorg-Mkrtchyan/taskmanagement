package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.entity.User;
import com.example.taskmanagement.model.enums.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import org.w3c.dom.stylesheets.LinkStyle;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);
    Optional<User> findByName(String name);
}
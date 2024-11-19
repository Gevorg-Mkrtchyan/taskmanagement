package com.example.taskmanagement.service;

import com.example.taskmanagement.model.dto.UserRequestDto;
import com.example.taskmanagement.model.dto.UserResponseDto;
import com.example.taskmanagement.model.entity.User;
import com.example.taskmanagement.model.enums.Role;
import com.example.taskmanagement.model.mapper.UserMapper;
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
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        log.info("Creating new user with email: {} and name: {}", userRequestDto.getEmail(), userRequestDto.getName());
        User user = userMapper.toEntity(userRequestDto);
        userRepository.save(user);
        log.info("User created with ID: {}", user.getId());
        return userMapper.toResponse(user);
    }

    public UserResponseDto getUserById(UUID userId) {
        log.info("Fetching user by ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID: {} not found", userId);
                    return new RuntimeException("User not found");
                });
        log.info("User found with ID: {}", userId);
        return userMapper.toResponse(user);
    }

    public UserResponseDto getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email: {} not found", email);
                    return new RuntimeException("User not found");
                });
        log.info("User found with email: {}", email);
        return userMapper.toResponse(user);
    }

    public List<UserResponseDto> getUsersByRole(Role role) {
        log.info("Fetching users by role: {}", role);
        List<User> users = userRepository.findByRole(role);
        log.info("Found {} users with role: {}", users.size(), role);
        return users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserByName(String name) {
        log.info("Fetching user by name: {}", name);
        User user = userRepository.findByName(name)
                .orElseThrow(() -> {
                    log.error("User with name: {} not found", name);
                    return new RuntimeException("User not found");
                });
        log.info("User found with name: {}", name);
        return userMapper.toResponse(user);
    }

    public List<UserResponseDto> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        log.info("Found {} users", users.size());
        return users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto updateUser(UUID userId, UserRequestDto userRequestDto) {
        log.info("Updating user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID: {} not found", userId);
                    return new RuntimeException("User not found");
                });

        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setName(userRequestDto.getName());
        user.setRole(userRequestDto.getRole());

        User updatedUser = userRepository.save(user);
        log.info("User with ID: {} updated successfully", userId);
        return userMapper.toResponse(updatedUser);
    }

    public void deleteUser(UUID userId) {
        log.info("Deleting user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID: {} not found", userId);
                    return new RuntimeException("User not found");
                });
        userRepository.delete(user);
        log.info("User with ID: {} deleted successfully", userId);
    }
}

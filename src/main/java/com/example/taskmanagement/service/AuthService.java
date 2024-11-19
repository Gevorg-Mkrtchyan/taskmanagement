package com.example.taskmanagement.service;

import com.example.taskmanagement.model.dto.AuthResponse;
import com.example.taskmanagement.model.mapper.UserMapper;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    @Value("${jwt.refresh-expiration}")
    private long refreshValidityInMilliseconds;

    public AuthService(UserRepository userRepository, UserMapper userMapper, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }


    /**
     * Аутентификация пользователя и получение токенов
     *
     * @param email email пользователя
     * @param password пароль пользователя
     * @return AuthResponse с токенами
     */
    public AuthResponse authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        String token = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(token);
        authResponse.setExpirationToken(String.valueOf(validityInMilliseconds));
        authResponse.setRefreshToken(refreshToken);

        return authResponse;
    }
}

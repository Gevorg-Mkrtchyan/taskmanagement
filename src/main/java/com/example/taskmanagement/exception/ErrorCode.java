package com.example.taskmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TOKEN_OUTDATED("error user token outdated", HttpStatus.NOT_ACCEPTABLE),
    TOKEN_INVALID( "error user token invalid", HttpStatus.NOT_ACCEPTABLE),
    INVALID_TOKEN("error request invalid token", HttpStatus.BAD_REQUEST);

    private final String messageKey;

    private final HttpStatus httpStatus;
}

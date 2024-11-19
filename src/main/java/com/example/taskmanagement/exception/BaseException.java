package com.example.taskmanagement.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    private final Throwable cause;

    private final Object[] args;

    private final String identifier;

    public BaseException(ErrorCode errorCode) {
        this(null, null, errorCode, null, null);
    }

    public BaseException(ErrorCode errorCode, String identifier) {
        this(null, null, errorCode, identifier, null);
    }

    public BaseException(ErrorCode errorCode, Object... args) {
        this(null, null, errorCode, null, args);
    }

    public BaseException(Throwable cause, String message, ErrorCode errorCode, String identifier, Object... args) {
        super(message);
        this.errorCode = errorCode;
        this.cause = cause;
        this.args = args;
        this.identifier = identifier;
    }

    public static void throwIf(boolean statement, ErrorCode code, Object... args) throws BaseException {
        if (statement) {
            throw new BaseException(code, args);
        }
    }

    public static void throwIf(boolean statement, ErrorCode code, String identifier) throws BaseException {
        if (statement) {
            throw new BaseException(code, identifier);
        }
    }

    @Override
    public String getMessage() {
        if (cause != null) {
            return cause.getMessage();
        }
        return super.getMessage();
    }
}

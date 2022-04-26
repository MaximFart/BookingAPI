package com.example.model.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RuntimeException {

    private HttpStatus httpStatus;

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public EntityNotFoundException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}

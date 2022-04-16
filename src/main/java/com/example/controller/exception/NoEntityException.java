package com.example.controller.exception;

public class NoEntityException extends Exception {

    public NoEntityException() {
    }

    public NoEntityException(String message) {
        super(message);
    }

    public NoEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.example.auth.exceptionhandling;

public class NoticeException extends RuntimeException {

    public NoticeException(String message) {
        super(message);
    }

    public NoticeException(String message, Throwable cause) {
        super(message, cause);
    }
}

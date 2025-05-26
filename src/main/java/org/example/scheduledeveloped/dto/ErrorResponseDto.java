package org.example.scheduledeveloped.dto;

import java.time.LocalDateTime;
import java.util.Map;


/**
 *
 */
public class ErrorResponseDto{
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final Map<String, String> errors; // 필드 에러 (nullable)

    public ErrorResponseDto(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

package com.moh.yehia.authenticationservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class ValidationError {
    private String statusCode;
    private String message;
    private String path;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public ValidationError() {
        timestamp = LocalDateTime.now();
        statusCode = "INVALID_REQUEST";
        errors = new HashMap<>();
    }

    public ValidationError(String path, String message) {
        this();
        this.path = path;
        this.message = message;
    }

    public void addError(String errorCode, String errorMessage) {
        errors.put(errorCode, errorMessage);
    }
}

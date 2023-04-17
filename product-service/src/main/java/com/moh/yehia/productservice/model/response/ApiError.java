package com.moh.yehia.productservice.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApiError {

    private String code;
    private String message;
    private String path;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime timestamp;

    public ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(String code, String message, String path) {
        this();
        this.code = code;
        this.message = message;
        this.path = path;
    }
}

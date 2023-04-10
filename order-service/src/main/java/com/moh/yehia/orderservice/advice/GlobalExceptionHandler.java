package com.moh.yehia.orderservice.advice;

import com.moh.yehia.orderservice.exception.InvalidOrderException;
import com.moh.yehia.orderservice.model.response.ApiError;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.TimeoutException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<ApiError> handleInvalidOrderException(InvalidOrderException e, WebRequest webRequest) {
        e.printStackTrace();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.name(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<ApiError> handleCallNotPermittedException(CallNotPermittedException e, WebRequest webRequest) {
        e.printStackTrace();
        ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE.name(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<ApiError> handleTimeoutException(Exception e, WebRequest webRequest) {
        e.printStackTrace();
        ApiError apiError = new ApiError(HttpStatus.REQUEST_TIMEOUT.name(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.REQUEST_TIMEOUT);
    }
}

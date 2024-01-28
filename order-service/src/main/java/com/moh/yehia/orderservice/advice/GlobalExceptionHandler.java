package com.moh.yehia.orderservice.advice;

import com.moh.yehia.orderservice.exception.InvalidOrderException;
import com.moh.yehia.orderservice.exception.InventoryServiceUnavailableException;
import com.moh.yehia.orderservice.model.response.ApiError;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.TimeoutException;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<ApiError> handleInvalidOrderException(InvalidOrderException e, WebRequest webRequest) {
        log.error(e.getMessage(), e);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.name(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InventoryServiceUnavailableException.class)
    public ResponseEntity<ApiError> handleInventoryServiceUnavailableException(InventoryServiceUnavailableException e, WebRequest webRequest) {
        log.error(e.getMessage(), e);
        ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE.name(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<ApiError> handleCallNotPermittedException(CallNotPermittedException e, WebRequest webRequest) {
        log.error(e.getMessage(), e);
        ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE.name(), "Service is not available now, Please try again later!", webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<ApiError> handleTimeoutException(Exception e, WebRequest webRequest) {
        log.error(e.getMessage(), e);
        ApiError apiError = new ApiError(HttpStatus.REQUEST_TIMEOUT.name(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.REQUEST_TIMEOUT);
    }
}

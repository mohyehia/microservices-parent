package com.moh.yehia.authenticationservice.advice;

import com.moh.yehia.authenticationservice.exception.UnAuthorizedException;
import com.moh.yehia.authenticationservice.model.ApiError;
import com.moh.yehia.authenticationservice.model.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UnAuthorizedException.class)
    protected ResponseEntity<ApiError> handleUnauthorizedException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        String message = ex.getMessage() == null || ex.getMessage().isEmpty() ? "Invalid Credentials" : ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED.name(), message, request.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, WebRequest request) {
        ValidationError validationError = new ValidationError(request.getDescription(false), "Invalid Request Data, Your request is either missing required data or contains invalid values");
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        fieldErrors.forEach(fieldError -> validationError.addError(fieldError.getField(), fieldError.getDefaultMessage()));
        globalErrors.forEach(globalError -> validationError.addError(globalError.getObjectName(), globalError.getDefaultMessage()));
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }
}

package com.moh.yehia.productservice.advice;

import com.moh.yehia.productservice.exception.InvalidRequestException;
import com.moh.yehia.productservice.model.response.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProductServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequestException(InvalidRequestException e, WebRequest webRequest) {
        e.printStackTrace();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.name(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}

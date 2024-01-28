package com.moh.yehia.productservice.advice;

import com.moh.yehia.productservice.exception.InvalidRequestException;
import com.moh.yehia.productservice.model.response.ApiError;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class ProductServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequestException(InvalidRequestException e, WebRequest webRequest) {
        log.error(e.getMessage(), e);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.name(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}

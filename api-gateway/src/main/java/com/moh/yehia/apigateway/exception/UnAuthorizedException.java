package com.moh.yehia.apigateway.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String message) {
        super(message);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}

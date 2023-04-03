package com.moh.yehia.apigateway.advice;

import com.moh.yehia.apigateway.exception.UnAuthorizedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new HashMap<>();
        Throwable throwable = getError(request);
        errorAttributes.put("message", throwable.getMessage());
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("path", request.path());

        MergedAnnotation<ResponseStatus> responseStatusMergedAnnotation = MergedAnnotations.from(throwable.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
        HttpStatus errorStatus = determineHttpStatus(throwable, responseStatusMergedAnnotation);
        errorAttributes.put("statusCode", errorStatus.name());
//        errorAttributes.put("requestId", request.exchange().getRequest().getId());
//        errorAttributes.put("method", request.methodName());
//        errorAttributes.put("error", errorStatus.getReasonPhrase());
//        errorAttributes.put("exception", throwable.getClass().getName());
        return errorAttributes;
    }

    private HttpStatus determineHttpStatus(Throwable throwable, MergedAnnotation<ResponseStatus> responseStatusMergedAnnotation) {
        if (throwable instanceof RuntimeException) {
            UnAuthorizedException unAuthorizedException = (UnAuthorizedException) throwable;
            return unAuthorizedException.getStatusCode();
        }
        return responseStatusMergedAnnotation.getValue("code", HttpStatus.class).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

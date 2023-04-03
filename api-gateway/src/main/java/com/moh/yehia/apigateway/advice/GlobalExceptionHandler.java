package com.moh.yehia.apigateway.advice;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {
    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageReaders(serverCodecConfigurer.getReaders());
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderException);
    }

    private Mono<ServerResponse> renderException(ServerRequest serverRequest) {
        Map<String, Object> errors = this.getErrorAttributes(serverRequest, ErrorAttributeOptions.defaults());
        errors.remove("requestId");
        errors.remove("status");
        return ServerResponse
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errors));
    }
}

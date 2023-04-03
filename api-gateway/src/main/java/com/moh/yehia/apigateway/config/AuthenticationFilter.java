package com.moh.yehia.apigateway.config;

import com.moh.yehia.apigateway.exception.UnAuthorizedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
@Log4j2
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final TokenValidator tokenValidator;

    public AuthenticationFilter(TokenValidator tokenValidator) {
        super(Config.class);
        this.tokenValidator = tokenValidator;
    }

    private static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth",
            "/eureka"
    );

    private final Predicate<ServerHttpRequest> isSecured = serverHttpRequest -> openApiEndpoints
            .stream()
            .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (isSecured.test(exchange.getRequest())) {
                log.info("accessing the secured microservice from api-gateway");
                // header contains access token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new UnAuthorizedException("Missing authorization header!");
                }
                List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
                if (authHeaders == null || authHeaders.isEmpty()) {
                    throw new UnAuthorizedException("Missing authorization header!");
                }
                String accessToken = authHeaders.get(0);
                if (accessToken.startsWith("Bearer ")) {
                    accessToken = accessToken.substring("Bearer ".length());
                    log.info("here calling the auth service to validate the access token!");
                    tokenValidator.validateAccessToken(accessToken);
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}

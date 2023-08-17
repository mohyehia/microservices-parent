package com.moh.yehia.apigateway.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class AccessTokenFilter extends AbstractGatewayFilterFactory<AccessTokenFilter.Config> {

    public AccessTokenFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info("AccessTokenFilter :: filter :: start");
            if (exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                List<String> authorizationList = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
                assert authorizationList != null;
                if (!authorizationList.isEmpty()) {
                    String authToken = authorizationList.get(0);
                    exchange.mutate().request(builder -> builder.headers(httpHeaders -> httpHeaders.add(HttpHeaders.AUTHORIZATION, authToken)));
                    return chain.filter(exchange);
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}

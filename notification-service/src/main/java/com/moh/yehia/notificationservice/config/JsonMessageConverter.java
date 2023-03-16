package com.moh.yehia.notificationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class JsonMessageConverter {
    @Bean
    public StringJsonMessageConverter stringJsonMessageConverter(){
        return new StringJsonMessageConverter();
    }
}

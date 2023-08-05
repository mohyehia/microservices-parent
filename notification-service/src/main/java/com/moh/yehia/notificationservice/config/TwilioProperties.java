package com.moh.yehia.notificationservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:twilio.yml")
@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioProperties {
    private String accountSid;
    private String authToken;
    private String phoneNumber;
}

package com.moh.yehia.notificationservice.config;

import com.twilio.Twilio;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class TwilioConfig {
    private final TwilioProperties twilioProperties;
    @PostConstruct
    public void init(){
        Twilio.init(twilioProperties.getAccountSid(), twilioProperties.getAuthToken());
    }
}

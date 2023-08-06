package com.moh.yehia.notificationservice.service.impl;

import com.moh.yehia.notificationservice.config.TwilioProperties;
import com.moh.yehia.notificationservice.service.design.SmsService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SmsServiceImpl implements SmsService {
    private final TwilioProperties twilioProperties;

    @Override
    public void sendSms(String orderNumber) {
        try {
            Message message = Message
                    .creator(
                            new PhoneNumber("+20123456789"),   // to
                            new PhoneNumber(twilioProperties.getPhoneNumber()), // from
                            "Your order has been successfully created and here is its number: " + orderNumber
                    )
                    .create();
            log.info("message ID =>{}", message.getSid());
            log.info("complete message sent from twilio =>{}", message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

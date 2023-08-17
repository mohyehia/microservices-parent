package com.moh.yehia.notificationservice.service.design;

import com.moh.yehia.notificationservice.model.OrderPlacedEvent;

public interface SmsService {
    void sendSms(OrderPlacedEvent orderPlacedEvent);
}

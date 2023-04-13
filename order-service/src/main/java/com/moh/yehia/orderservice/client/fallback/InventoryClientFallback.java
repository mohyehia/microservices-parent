package com.moh.yehia.orderservice.client.fallback;

import com.moh.yehia.orderservice.client.InventoryClient;
import com.moh.yehia.orderservice.exception.InventoryServiceUnavailableException;
import com.moh.yehia.orderservice.model.request.OrderLineInquiry;
import com.moh.yehia.orderservice.model.response.InventoryResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class InventoryClientFallback implements InventoryClient {
    @Override
    public List<InventoryResponse> productInStock(List<OrderLineInquiry> orderLineInquiry) {
        log.error("implementing the fallback method starts here!");
        throw new InventoryServiceUnavailableException();
    }
}

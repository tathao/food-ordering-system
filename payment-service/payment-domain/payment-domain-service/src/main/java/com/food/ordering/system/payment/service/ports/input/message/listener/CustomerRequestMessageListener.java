package com.food.ordering.system.payment.service.ports.input.message.listener;

import com.food.ordering.system.payment.service.dto.CustomerRequest;

public interface CustomerRequestMessageListener {
    void createCustomer(CustomerRequest customerRequest);
}

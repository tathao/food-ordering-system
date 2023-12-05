package com.food.ordering.system.payment.service.impl;

import com.food.ordering.system.payment.service.dto.CustomerRequest;
import com.food.ordering.system.payment.service.helper.CustomerRequestHelper;
import com.food.ordering.system.payment.service.ports.input.message.listener.CustomerRequestMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerRequestMessageListenerImpl implements CustomerRequestMessageListener {
    private final CustomerRequestHelper customerRequestHelper;
    @Override
    public void createCustomer(CustomerRequest customerRequest) {
        customerRequestHelper.createCustomer(customerRequest);
    }
}

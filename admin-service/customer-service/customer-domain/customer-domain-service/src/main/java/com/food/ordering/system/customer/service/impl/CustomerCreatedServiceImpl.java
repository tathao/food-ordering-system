package com.food.ordering.system.customer.service.impl;

import com.food.ordering.system.customer.service.CustomerCreatedService;
import com.food.ordering.system.customer.service.dto.create.CreateCustomerRequest;
import com.food.ordering.system.customer.service.dto.create.CreateCustomerResponse;
import com.food.ordering.system.customer.service.helper.CustomerCreateHelper;
import com.food.ordering.system.customer.service.mapper.CustomerDataMapper;
import com.food.ordering.system.customer.service.ports.output.message.publisher.CustomerMessagePublisher;
import com.food.ordering.system.order.domain.event.CustomerEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class CustomerCreatedServiceImpl implements CustomerCreatedService {
    private final CustomerCreateHelper customerCreateHelper;
    private final CustomerDataMapper customerDataMapper;
    private final CustomerMessagePublisher customerMessagePublisher;
    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerRequest request) {
        CustomerEvent customerEvent = customerCreateHelper.createCustomer(request);
        customerMessagePublisher.publish(customerEvent);
        return customerDataMapper.customerToCreateCustomerResponse(customerEvent.getCustomer(),
                "Customer saved successful!");
    }
}

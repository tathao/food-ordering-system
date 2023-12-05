package com.food.ordering.system.customer.service;

import com.food.ordering.system.customer.service.dto.create.CreateCustomerRequest;
import com.food.ordering.system.customer.service.dto.create.CreateCustomerResponse;

import javax.validation.Valid;

public interface CustomerCreatedService {
    CreateCustomerResponse createCustomer(@Valid CreateCustomerRequest createCustomerCommand);
}

package com.food.ordering.system.customer.service.mapper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.customer.service.dto.create.CreateCustomerRequest;
import com.food.ordering.system.customer.service.dto.create.CreateCustomerResponse;
import com.food.ordering.system.order.domain.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerDataMapper {

    public Customer createCustomerRequestToCustomer(CreateCustomerRequest request) {
        return Customer.builder()
                .id(new CustomerId(UUID.randomUUID()))
                .userName(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .amount(new Money(request.getAmount()))
                .active(true)
                .build();
    }

    public CreateCustomerResponse customerToCreateCustomerResponse(Customer customer, String message) {
        return new CreateCustomerResponse(customer.getId().getValue(), message);
    }
}

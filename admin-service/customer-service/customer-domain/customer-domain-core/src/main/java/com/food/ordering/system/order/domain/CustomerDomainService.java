package com.food.ordering.system.order.domain;

import com.food.ordering.system.order.domain.entity.Customer;
import com.food.ordering.system.order.domain.event.CustomerEvent;

public interface CustomerDomainService {

    CustomerEvent validateAndInitiateCustomer(Customer customer);
}

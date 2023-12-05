package com.food.ordering.system.customer.service.ports.output.repository;

import com.food.ordering.system.order.domain.entity.Customer;

public interface CustomerRepository {

    Customer createCustomer(Customer customer);
}

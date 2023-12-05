package com.food.ordering.system.customer.db.mapper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.customer.db.entity.CustomerEntity;
import com.food.ordering.system.order.domain.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return Customer.builder()
                .id(new CustomerId(customerEntity.getId()))
                .userName(customerEntity.getUserName())
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .amount(new Money(customerEntity.getAmount()))
                .active(customerEntity.isActive())
                .build();
    }

    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId().getValue())
                .userName(customer.getUserName())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .amount(customer.getAmount().getAmount())
                .active(customer.isActive())
                .build();
    }
}

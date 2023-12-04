package com.food.ordering.system.order.domain.event;

import com.food.ordering.system.common.domain.event.DomainEvent;
import com.food.ordering.system.order.domain.entity.Customer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class CustomerEvent implements DomainEvent<Customer> {
    @Getter
    private final Customer customer;
    private final ZonedDateTime createdAt;
}

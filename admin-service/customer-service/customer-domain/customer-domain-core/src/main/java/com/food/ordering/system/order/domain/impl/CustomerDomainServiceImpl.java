package com.food.ordering.system.order.domain.impl;

import com.food.ordering.system.order.domain.CustomerDomainService;
import com.food.ordering.system.order.domain.entity.Customer;
import com.food.ordering.system.order.domain.event.CustomerEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.food.ordering.system.common.domain.constant.DomainConstant.UTC;

@Slf4j
public class CustomerDomainServiceImpl implements CustomerDomainService {
    @Override
    public CustomerEvent validateAndInitiateCustomer(Customer customer) {

        //Any Business logic required to run for a customer creation
        log.info("Customer with id: {} is initiated", customer.getId().getValue());
        return new CustomerEvent(customer, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}

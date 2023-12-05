package com.food.ordering.system.payment.domain.exception;

import com.food.ordering.system.common.domain.exception.DomainException;

public class CustomerNotFoundException extends DomainException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}

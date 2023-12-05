package com.food.ordering.system.order.domain.exception;

import com.food.ordering.system.common.domain.exception.DomainException;

public class CustomerDomainException extends DomainException {
    public CustomerDomainException(String message) {
        super(message);
    }
}

package com.food.ordering.system.customer.db.exception;

import com.food.ordering.system.common.domain.exception.DomainException;

public class CustomerDataAccessException extends DomainException {
    public CustomerDataAccessException(String message) {
        super(message);
    }
}

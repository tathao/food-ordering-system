package com.food.ordering.system.payment.db.exception;

import com.food.ordering.system.common.domain.exception.DomainException;

public class OrderOutboxNotFoundException extends DomainException {
    public OrderOutboxNotFoundException(String message) {
        super(message);
    }

    public OrderOutboxNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.food.ordering.system.order.db.exception;

import com.food.ordering.system.common.domain.exception.DomainException;

public class PaymentOutboxNotFoundException extends DomainException {
    public PaymentOutboxNotFoundException(String message) {
        super(message);
    }

    public PaymentOutboxNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

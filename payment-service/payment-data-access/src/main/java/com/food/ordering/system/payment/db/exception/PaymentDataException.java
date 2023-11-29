package com.food.ordering.system.payment.db.exception;

import com.food.ordering.system.common.domain.exception.DomainException;

public class PaymentDataException extends DomainException {
    public PaymentDataException(String message) {
        super(message);
    }

    public PaymentDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

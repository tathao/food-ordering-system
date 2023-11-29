package com.food.ordering.system.payment.service.exception;

import com.food.ordering.system.common.domain.exception.DomainException;

public class PaymentDomainServiceException extends DomainException {
    public PaymentDomainServiceException(String message) {
        super(message);
    }

    public PaymentDomainServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

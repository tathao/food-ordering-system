package com.food.ordering.system.order.db.exception;

import com.food.ordering.system.common.domain.exception.DomainException;

public class ApprovalOutboxNotFoundException extends DomainException {
    public ApprovalOutboxNotFoundException(String message) {
        super(message);
    }

    public ApprovalOutboxNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

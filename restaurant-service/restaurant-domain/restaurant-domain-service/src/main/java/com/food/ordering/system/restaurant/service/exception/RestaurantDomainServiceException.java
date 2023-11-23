package com.food.ordering.system.restaurant.service.exception;

import com.food.ordering.system.common.domain.exception.DomainException;

public class RestaurantDomainServiceException extends DomainException {
    public RestaurantDomainServiceException(String message) {
        super(message);
    }

    public RestaurantDomainServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

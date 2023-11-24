package com.food.ordering.system.restaurant.data.exception;

import com.food.ordering.system.common.domain.exception.DomainException;

public class RestaurantDataAccessNotFoundException extends DomainException {
    public RestaurantDataAccessNotFoundException(String message) {
        super(message);
    }

    public RestaurantDataAccessNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

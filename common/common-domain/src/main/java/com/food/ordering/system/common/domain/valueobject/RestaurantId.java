package com.food.ordering.system.common.domain.valueobject;

import java.util.UUID;

public class RestaurantId extends BaseId<UUID>{
    public RestaurantId(final UUID value) {
        super(value);
    }
}

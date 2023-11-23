package com.food.ordering.system.restaurant.domain.valuobject;

import com.food.ordering.system.common.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderApprovalId extends BaseId<UUID> {
    public OrderApprovalId(UUID value) {
        super(value);
    }
}

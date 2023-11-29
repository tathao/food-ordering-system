package com.food.ordering.system.payment.domain.valueobject;

import com.food.ordering.system.common.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditId extends BaseId<UUID> {
    public CreditId(UUID value) {
        super(value);
    }
}

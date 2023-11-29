package com.food.ordering.system.payment.domain.valueobject;

import com.food.ordering.system.common.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditHistoryId extends BaseId<UUID> {
    public CreditHistoryId(UUID value) {
        super(value);
    }
}

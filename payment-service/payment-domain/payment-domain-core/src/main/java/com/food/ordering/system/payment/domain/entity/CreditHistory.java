package com.food.ordering.system.payment.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.payment.domain.valueobject.CreditHistoryId;
import com.food.ordering.system.payment.domain.valueobject.TransactionType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class CreditHistory extends BaseEntity<CreditHistoryId> {

    private final CustomerId customerId;
    private final Money amount;
    private final TransactionType transactionType;

    private CreditHistory(final CreditHistoryBuilder builder) {
        super.setId(builder.creditHistoryId);
        this.customerId = builder.customerId;
        this.amount = builder.amount;
        this.transactionType = builder.transactionType;
    }

    public static CreditHistoryBuilder builder() {
        return new CreditHistoryBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class CreditHistoryBuilder {
        private CreditHistoryId creditHistoryId;
        private CustomerId customerId;
        private Money amount;
        private TransactionType transactionType;

        public CreditHistory build() {
            return new CreditHistory(this);
        }
    }
}

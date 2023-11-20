package com.food.ordering.system.payment.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.payment.domain.valueobject.CreditId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class Credit extends BaseEntity<CreditId> {

    private final CustomerId customerId;
    private Money totalCreditAmount;

    public void addCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.add(amount);
    }

    public void subtractCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }

    private Credit(final CreditBuilder builder) {
        super.setId(builder.creditEntryId);
        this.customerId = builder.customerId;
        this.totalCreditAmount = builder.totalCreditAmount;
    }

    public static CreditBuilder builder() {
        return new CreditBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class CreditBuilder {
        private CreditId creditEntryId;
        private CustomerId customerId;
        private Money totalCreditAmount;

        public Credit build() {
            return new Credit(this);
        }
    }
}

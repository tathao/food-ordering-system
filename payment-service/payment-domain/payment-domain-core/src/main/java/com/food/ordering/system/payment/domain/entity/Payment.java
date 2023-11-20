package com.food.ordering.system.payment.domain.entity;

import com.food.ordering.system.common.domain.entity.AggregateRoot;
import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.OrderId;
import com.food.ordering.system.common.domain.valueobject.PaymentStatus;
import com.food.ordering.system.payment.domain.valueobject.PaymentId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static com.food.ordering.system.common.domain.constant.DomainConstant.UTC;

@Getter
public class Payment extends AggregateRoot<PaymentId> {
    private final OrderId orderId;
    private final CustomerId customerId;
    private final Money amount;

    private PaymentStatus paymentStatus;
    private ZonedDateTime createdAt;

    public void initializePayment() {
        setId(new PaymentId(UUID.randomUUID()));
        createdAt = ZonedDateTime.now(ZoneId.of(UTC));
    }

    public void validatePayment(List<String> failureMessages) {
        if (amount == null || !amount.isGreaterThanZero()) {
            failureMessages.add("Total price must be greater than zero!");
        }
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    private Payment(final PaymentBuilder builder) {
        super.setId(builder.paymentId);
        this.orderId = builder.orderId;
        this.customerId = builder.customerId;
        this.amount = builder.amount;
        this.paymentStatus = builder.paymentStatus;
        this.createdAt = builder.createdAt;
    }

    @Setter
    @Accessors(fluent = true)
    public static final class PaymentBuilder {
        private PaymentId paymentId;
        private OrderId orderId;
        private CustomerId customerId;
        private Money amount;
        private PaymentStatus paymentStatus;
        private ZonedDateTime createdAt;

        public Payment build() {
            return new Payment(this);
        }
    }
}

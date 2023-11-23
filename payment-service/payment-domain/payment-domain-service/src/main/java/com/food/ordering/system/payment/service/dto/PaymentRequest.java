package com.food.ordering.system.payment.service.dto;

import com.food.ordering.system.common.domain.valueobject.PaymentOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class PaymentRequest {

    private String id;
    private String sagaId;
    private String orderId;
    private String restaurantId;
    private String customerId;
    private BigDecimal amount;
    private Instant createdAt;
    @Setter
    private PaymentOrderStatus paymentOrderStatus;
}

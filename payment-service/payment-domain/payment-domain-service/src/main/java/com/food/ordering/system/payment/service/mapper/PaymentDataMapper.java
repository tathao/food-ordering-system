package com.food.ordering.system.payment.service.mapper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.OrderId;
import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.payment.domain.entity.Payment;
import com.food.ordering.system.payment.service.dto.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {
    public Payment paymentRequestToPayment(PaymentRequest paymentRequest) {

        return Payment.builder()
                .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .customerId(new CustomerId(UUID.fromString(paymentRequest.getCustomerId())))
                .restaurantId(new RestaurantId(UUID.fromString(paymentRequest.getOrderId())))
                .amount(new Money(paymentRequest.getAmount()))
                .build();
    }
}

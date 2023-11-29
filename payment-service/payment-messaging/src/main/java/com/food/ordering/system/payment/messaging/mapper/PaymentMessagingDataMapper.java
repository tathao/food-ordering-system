package com.food.ordering.system.payment.messaging.mapper;

import com.food.ordering.system.common.domain.valueobject.PaymentOrderStatus;
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel;
import com.food.ordering.system.kafka.order.avro.model.PaymentStatus;
import com.food.ordering.system.payment.domain.event.PaymentCompletedEvent;
import com.food.ordering.system.payment.service.dto.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMessagingDataMapper {
    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .id(paymentRequestAvroModel.getId())
                .sagaId(paymentRequestAvroModel.getSagaId())
                .customerId(paymentRequestAvroModel.getCustomerId())
                .orderId(paymentRequestAvroModel.getOrderId())
                .restaurantId(paymentRequestAvroModel.getRestaurantId())
                .amount(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreateAt())
                .paymentOrderStatus(PaymentOrderStatus.valueOf(paymentRequestAvroModel.getPaymentOrderStatus().name()))
                .build();
    }

    public PaymentResponseAvroModel paymentCompletedEventToPaymentResponseAvroModel(PaymentCompletedEvent domainEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(domainEvent.getPayment().getCustomerId().getValue().toString())
                .setRestaurantId(domainEvent.getPayment().getRestaurantId().getValue().toString())
                .setPaymentId(domainEvent.getPayment().getId().getValue().toString())
                .setPrice(domainEvent.getPayment().getAmount().getAmount())
                .setOrderId(domainEvent.getPayment().getOrderId().getValue().toString())
                .setCreatedAt(domainEvent.getPayment().getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(domainEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(domainEvent.getFailureMessages())
                .build();
    }
}

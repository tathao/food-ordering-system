package com.food.ordering.system.payment.messaging.mapper;

import com.food.ordering.system.common.domain.valueobject.PaymentOrderStatus;
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel;
import com.food.ordering.system.kafka.order.avro.model.PaymentStatus;
import com.food.ordering.system.payment.domain.event.PaymentCompletedEvent;
import com.food.ordering.system.payment.domain.event.PaymentEvent;
import com.food.ordering.system.payment.domain.event.PaymentFailedEvent;
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
        return mapPaymentEventToPaymentResponseAvroModel(domainEvent);
    }

    public PaymentResponseAvroModel
    paymentFailedEventToPaymentResponseAvroModel(PaymentFailedEvent domainEvent) {
        return mapPaymentEventToPaymentResponseAvroModel(domainEvent);
    }

    private PaymentResponseAvroModel mapPaymentEventToPaymentResponseAvroModel(PaymentEvent paymentEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(paymentEvent.getPayment().getCustomerId().getValue().toString())
                .setRestaurantId(paymentEvent.getPayment().getRestaurantId().getValue().toString())
                .setPaymentId(paymentEvent.getPayment().getId().getValue().toString())
                .setPrice(paymentEvent.getPayment().getAmount().getAmount())
                .setOrderId(paymentEvent.getPayment().getOrderId().getValue().toString())
                .setCreatedAt(paymentEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentEvent.getFailureMessages())
                .build();
    }
}

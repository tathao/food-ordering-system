package com.food.ordering.system.order.messaging.mapper;

import com.food.ordering.system.common.domain.valueobject.PaymentStatus;
import com.food.ordering.system.kafka.order.avro.model.*;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.entity.OrderItem;
import com.food.ordering.system.order.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.dto.message.RestaurantApprovalResponse;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {
    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent domainEvent) {
        Order order = domainEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getTotalAmount().getAmount())
                .setRestaurantId(order.getRestaurantId().getValue().toString())
                .setCreateAt(domainEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();
    }

    public PaymentResponse paymentAvroResponseModelToPaymentResponse(PaymentResponseAvroModel paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId())
                .sagaId(paymentResponseAvroModel.getSagaId())
                .orderId(paymentResponseAvroModel.getOrderId())
                .restaurantId(paymentResponseAvroModel.getRestaurantId())
                .price(paymentResponseAvroModel.getPrice())
                .paymentStatus(PaymentStatus.valueOf(paymentResponseAvroModel.getPaymentStatus().name()))
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel(OrderPaidEvent domainEvent) {
        Order order = domainEvent.getOrder();
        Map<UUID, Integer> products = new HashMap<>();
        order.getItems().forEach(orderItem -> products.put(orderItem.getProduct().getId().getValue(), orderItem.getQuantity()));
        return RestaurantApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getTotalAmount().getAmount())
                .setRestaurantId(order.getRestaurantId().getValue().toString())
                .setProducts(productsToAvroProducts(products))
                .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
                .setCreatedAt(domainEvent.getCreatedAt().toInstant())
                .build();
    }

    private List<com.food.ordering.system.kafka.order.avro.model.Product> productsToAvroProducts(Map<UUID, Integer> products) {
        return products.entrySet()
                .stream().map(
                        entry -> productToAvroProduct(entry.getKey(), entry.getValue())
                ).collect(Collectors.toList());
    }

    private com.food.ordering.system.kafka.order.avro.model.Product productToAvroProduct(UUID productId, Integer quantity) {
        return com.food.ordering.system.kafka.order.avro.model.Product.newBuilder()
                .setId(productId.toString())
                .setQuantity(quantity)
                .build();
    }

    public RestaurantApprovalResponse approvalResponseAvroModelToApprovalResponse(RestaurantApprovalResponseAvroModel restaurantApprovalResponseAvroModel) {
        return RestaurantApprovalResponse.builder()
                .id(restaurantApprovalResponseAvroModel.getId())
                .sagaId(restaurantApprovalResponseAvroModel.getSagaId())
                .restaurantId(restaurantApprovalResponseAvroModel.getRestaurantId())
                .orderId(restaurantApprovalResponseAvroModel.getOrderId())
                .createdAt(restaurantApprovalResponseAvroModel.getCreatedAt())
                .orderApprovalStatus(com.food.ordering.system.common.domain.valueobject.OrderApprovalStatus.valueOf(
                        restaurantApprovalResponseAvroModel.getOrderApprovalStatus().name()))
                .failureMessages(restaurantApprovalResponseAvroModel.getFailureMessages())
                .build();
    }

    public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent) {
        Order order = orderCancelledEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getTotalAmount().getAmount())
                .setCreateAt(orderCancelledEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                .build();
    }
}

package com.food.ordering.system.order.service.handler;

import com.food.ordering.system.order.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.helper.OrderCreateHelper;
import com.food.ordering.system.order.service.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.ports.output.message.publisher.payment.OrderCreatePaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateCommandHandler {
    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatePaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;
    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderRequest);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(),
                "Order created successfully");
    }
}

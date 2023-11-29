package com.food.ordering.system.order.domain;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(final Order order,  DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher);
    OrderPaidEvent payOrder(final Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher);

    OrderCancelledEvent cancelOrderPayment(final Order order, List<String> failureMessages,
                                           DomainEventPublisher<OrderCancelledEvent>orderCancelledEventDomainEventPublisher);

    void approveOrder(Order order);

    void cancelOrder(Order order, List<String> failureMessages);
}

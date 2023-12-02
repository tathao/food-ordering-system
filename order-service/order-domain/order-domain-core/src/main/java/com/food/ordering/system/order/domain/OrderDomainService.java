package com.food.ordering.system.order.domain;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(final Order order);
    OrderPaidEvent payOrder(final Order order);

    OrderCancelledEvent cancelOrderPayment(final Order order, List<String> failureMessages);

    void approveOrder(Order order);

    void cancelOrder(Order order, List<String> failureMessages);
}

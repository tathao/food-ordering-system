package com.food.ordering.system.order.domain.event;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent{

    private final DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher;

    public OrderCancelledEvent(Order order, ZonedDateTime createdAt,
                               DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher) {
        super(order, createdAt);
        this.orderCancelledEventDomainEventPublisher = orderCancelledEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderCancelledEventDomainEventPublisher.publish(this);
    }
}

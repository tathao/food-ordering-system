package com.food.ordering.system.order.domain.event;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent{

    private final DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher;
    public OrderPaidEvent(final Order order, final ZonedDateTime createdAt,
                          final DomainEventPublisher<OrderPaidEvent> orderCreatedEventDomainEventPublisher) {
        super(order, createdAt);
        this.orderPaidEventDomainEventPublisher = orderCreatedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderPaidEventDomainEventPublisher.publish(this);
    }
}

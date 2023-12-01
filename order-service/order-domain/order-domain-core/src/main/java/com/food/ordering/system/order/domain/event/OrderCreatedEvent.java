package com.food.ordering.system.order.domain.event;

import com.food.ordering.system.order.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent{

    public OrderCreatedEvent(final Order order, final ZonedDateTime createdAt) {
        super(order, createdAt);
    }

    @Override
    public void fire() {
    }
}

package com.food.ordering.system.order.domain.event;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent{

    public OrderPaidEvent(final Order order, final ZonedDateTime createdAt) {
        super(order, createdAt);
    }

}

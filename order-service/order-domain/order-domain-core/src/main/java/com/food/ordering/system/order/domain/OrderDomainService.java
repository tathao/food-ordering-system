package com.food.ordering.system.order.domain;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.entity.Product;
import com.food.ordering.system.order.domain.event.OrderCreatedEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(final Order order, long theLastOfOrderItemId, List<Product> products, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher);
}

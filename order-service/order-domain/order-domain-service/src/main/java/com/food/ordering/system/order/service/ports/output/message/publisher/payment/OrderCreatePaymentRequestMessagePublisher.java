package com.food.ordering.system.order.service.ports.output.message.publisher.payment;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.domain.event.OrderCreatedEvent;

public interface OrderCreatePaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}

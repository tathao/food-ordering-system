package com.food.ordering.system.order.service.ports.output.message.publisher.payment;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.domain.event.OrderPaidEvent;

public interface OrderApprovalRestaurantMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}

package com.food.ordering.system.payment.service.ports.output.message.publisher;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.payment.domain.event.PaymentCompletedEvent;

public interface PaymentCompleteMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent> {
}

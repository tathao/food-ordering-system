package com.food.ordering.system.payment.messaging.publisher.kafka;

import com.food.ordering.system.payment.domain.event.PaymentFailedEvent;
import com.food.ordering.system.payment.service.ports.output.message.publisher.PaymentFailedMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentFailedKafkaMessagePublisher implements PaymentFailedMessagePublisher {
    @Override
    public void publish(PaymentFailedEvent domainEvent) {

    }
}

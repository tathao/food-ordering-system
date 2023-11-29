package com.food.ordering.system.restaurant.messaging.publisher.kafka;

import com.food.ordering.system.restaurant.domain.event.OrderRejectedEvent;
import com.food.ordering.system.restaurant.service.ports.output.message.publisher.OrderRejectedMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderRejectedKafkaMessagePublisher implements OrderRejectedMessagePublisher {
    @Override
    public void publish(OrderRejectedEvent domainEvent) {

    }
}

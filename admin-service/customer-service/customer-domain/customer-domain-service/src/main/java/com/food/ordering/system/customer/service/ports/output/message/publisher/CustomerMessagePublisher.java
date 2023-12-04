package com.food.ordering.system.customer.service.ports.output.message.publisher;

import com.food.ordering.system.order.domain.event.CustomerEvent;

public interface CustomerMessagePublisher {

    void publish(CustomerEvent customerEvent);
}

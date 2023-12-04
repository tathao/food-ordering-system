package com.food.ordering.system.customer.messaging.mapper;

import com.food.ordering.system.kafka.order.avro.model.CustomerAvroModel;
import com.food.ordering.system.order.domain.event.CustomerEvent;
import org.springframework.stereotype.Component;

@Component
public class CustomerMessagingDataMapper {

    public CustomerAvroModel paymentResponseAvroModelToPaymentResponse(CustomerEvent
                                                                               customerEvent) {
        return CustomerAvroModel.newBuilder()
                .setId(customerEvent.getCustomer().getId().getValue().toString())
                .setUsername(customerEvent.getCustomer().getUserName())
                .setFirstName(customerEvent.getCustomer().getFirstName())
                .setLastName(customerEvent.getCustomer().getLastName())
                .setActive(customerEvent.getCustomer().isActive())
                .setPrice(customerEvent.getCustomer().getAmount().getAmount())
                .build();
    }
}

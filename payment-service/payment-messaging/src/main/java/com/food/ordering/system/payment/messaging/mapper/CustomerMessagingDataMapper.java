package com.food.ordering.system.payment.messaging.mapper;

import com.food.ordering.system.kafka.order.avro.model.CustomerAvroModel;
import com.food.ordering.system.payment.service.dto.CustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerMessagingDataMapper {

    public CustomerRequest customerRequestAvroModelToCustomerRequest(CustomerAvroModel customerAvroModel) {
        return CustomerRequest.builder()
                .customerId(customerAvroModel.getId())
                .userName(customerAvroModel.getUsername())
                .firstName(customerAvroModel.getFirstName())
                .lastName(customerAvroModel.getLastName())
                .amount(customerAvroModel.getPrice())
                .active(customerAvroModel.getActive())
                .build();
    }
}

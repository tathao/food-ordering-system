package com.food.ordering.system.payment.service.mapper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.payment.domain.entity.Credit;
import com.food.ordering.system.payment.domain.valueobject.CreditId;
import com.food.ordering.system.payment.service.dto.CustomerRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerDataMapper {

    public Credit customerRequestToCredit(CustomerRequest customerRequest) {

        return Credit.builder()
                .creditEntryId(new CreditId(UUID.randomUUID()))
                .customerId(new CustomerId(UUID.fromString(customerRequest.getCustomerId())))
                .userName(customerRequest.getUserName())
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .active(customerRequest.isActive())
                .totalCreditAmount(new Money(customerRequest.getAmount()))
                .build();
    }
}

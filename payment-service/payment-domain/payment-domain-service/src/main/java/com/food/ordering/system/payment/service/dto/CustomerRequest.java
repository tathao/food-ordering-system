package com.food.ordering.system.payment.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class CustomerRequest {
    private String customerId;
    private String firstName;
    private String lastName;
    private BigDecimal amount;
}

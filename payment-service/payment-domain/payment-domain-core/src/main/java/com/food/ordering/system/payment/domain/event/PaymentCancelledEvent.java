package com.food.ordering.system.payment.domain.event;

import com.food.ordering.system.payment.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCancelledEvent extends PaymentEvent{

    public PaymentCancelledEvent(Payment payment, ZonedDateTime createdAt){
        super(payment, createdAt, Collections.emptyList());
    }

}

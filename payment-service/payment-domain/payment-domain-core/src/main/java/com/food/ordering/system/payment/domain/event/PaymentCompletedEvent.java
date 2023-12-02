package com.food.ordering.system.payment.domain.event;

import com.food.ordering.system.payment.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCompletedEvent extends PaymentEvent{

    public PaymentCompletedEvent(Payment payment, ZonedDateTime createdAt){
        super(payment, createdAt, Collections.emptyList());
    }

}

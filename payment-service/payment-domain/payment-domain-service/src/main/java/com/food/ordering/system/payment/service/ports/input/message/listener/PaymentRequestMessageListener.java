package com.food.ordering.system.payment.service.ports.input.message.listener;

import com.food.ordering.system.payment.service.dto.PaymentRequest;

public interface PaymentRequestMessageListener {

    void completePayment(PaymentRequest paymentRequest);
}

package com.food.ordering.system.payment.service;

import com.food.ordering.system.payment.service.dto.PaymentRequest;

public interface PaymentDomainApplicationService {

    void completePayment(PaymentRequest paymentRequest);

    String creditPayment(PaymentRequest paymentRequest);
}
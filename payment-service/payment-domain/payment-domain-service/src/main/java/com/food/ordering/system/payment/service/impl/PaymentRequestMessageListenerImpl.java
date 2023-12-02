package com.food.ordering.system.payment.service.impl;

import com.food.ordering.system.payment.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.dto.PaymentRequest;

import com.food.ordering.system.payment.service.helper.PaymentRequestHelper;
import com.food.ordering.system.payment.service.ports.input.message.listener.PaymentRequestMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHelper paymentRequestHelper;

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        paymentRequestHelper.persistCompletePayment(paymentRequest);
    }

    @Override
    public void cancelledPayment(PaymentRequest paymentRequest) {
        paymentRequestHelper.persistCancelPayment(paymentRequest);
    }

}

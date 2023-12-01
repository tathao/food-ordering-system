package com.food.ordering.system.order.service.impl;

import com.food.ordering.system.order.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.ports.input.message.listener.payment.PaymentResponseListener;
import com.food.ordering.system.order.service.saga.OrderPaymentSaga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.food.ordering.system.common.domain.constant.DomainConstant.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentResponseListenerImpl implements PaymentResponseListener {

    private final OrderPaymentSaga orderPaymentSaga;

    @Override
    public void paymentComplete(PaymentResponse paymentResponse) {
        orderPaymentSaga.process(paymentResponse);
        log.info("Order Payment Saga process operation is completed for order id: {}", paymentResponse.getOrderId());
    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {
        orderPaymentSaga.rollback(paymentResponse);
        log.info("Order is roll backed for order id: {} with failure message: {}",
                paymentResponse.getOrderId(),
                String.join(FAILURE_MESSAGE_DELIMITER, paymentResponse.getFailureMessages()));
    }

}

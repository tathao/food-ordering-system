package com.food.ordering.system.order.service.impl;

import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.handler.OrderApprovalCommandHandler;
import com.food.ordering.system.order.service.ports.input.message.listener.payment.OrderApprovalMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderApprovalMessageListenerImpl implements OrderApprovalMessageListener {

    private final OrderApprovalCommandHandler orderApprovalCommandHandler;

    @Override
    public void orderApprovalToRestaurant(PaymentResponse paymentResponse) {
        orderApprovalCommandHandler.orderApprovalToRestaurant(paymentResponse);
    }
}

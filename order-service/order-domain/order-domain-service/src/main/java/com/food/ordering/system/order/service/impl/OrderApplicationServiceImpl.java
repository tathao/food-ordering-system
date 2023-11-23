package com.food.ordering.system.order.service.impl;

import com.food.ordering.system.order.service.OrderApplicationService;
import com.food.ordering.system.order.service.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.handler.OrderCreateCommandHandler;
import com.food.ordering.system.order.service.ports.input.message.listener.payment.OrderApprovalMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderApprovalMessageListener orderApprovalMessageListener;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        return orderCreateCommandHandler.createOrder(createOrderRequest);
    }

    @Override
    public void orderApprovalToRestaurant(PaymentResponse paymentResponse) {
        orderApprovalMessageListener.orderApprovalToRestaurant(paymentResponse);
    }
}

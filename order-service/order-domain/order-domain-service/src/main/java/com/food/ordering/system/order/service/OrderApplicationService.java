package com.food.ordering.system.order.service;

import com.food.ordering.system.order.service.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.dto.message.PaymentResponse;

import javax.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderRequest createOrderRequest);

    void orderApprovalToRestaurant(PaymentResponse paymentResponse);
}

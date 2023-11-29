package com.food.ordering.system.order.service.impl;

import com.food.ordering.system.order.service.OrderApplicationService;
import com.food.ordering.system.order.service.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.service.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.handler.OrderCreateCommandHandler;
import com.food.ordering.system.order.service.handler.OrderTrackCommandHandler;
import com.food.ordering.system.order.service.ports.input.message.listener.payment.PaymentResponseListener;
import com.food.ordering.system.order.service.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
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
    private final OrderTrackCommandHandler orderTrackCommandHandler;
    private final PaymentResponseListener paymentResponseListener;
    private final RestaurantApprovalResponseMessageListener restaurantApprovalResponseMessageListener;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        return orderCreateCommandHandler.createOrder(createOrderRequest);
    }

    @Override
    public void orderApprovalToRestaurant(PaymentResponse paymentResponse) {
        paymentResponseListener.paymentComplete(paymentResponse);
    }

    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {
        restaurantApprovalResponseMessageListener.orderApproved(restaurantApprovalResponse);
    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {
        restaurantApprovalResponseMessageListener.orderRejected(restaurantApprovalResponse);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery build) {
        return orderTrackCommandHandler.trackOrder(build);
    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {
        paymentResponseListener.paymentCancelled(paymentResponse);
    }
}

package com.food.ordering.system.order.service;

import com.food.ordering.system.order.service.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.service.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderRequest createOrderRequest);

    void orderApprovalToRestaurant(PaymentResponse paymentResponse);

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);

    TrackOrderResponse trackOrder(TrackOrderQuery build);
}

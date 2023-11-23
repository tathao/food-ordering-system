package com.food.ordering.system.restaurant.service.impl;

import com.food.ordering.system.restaurant.domain.event.OrderApprovalEvent;
import com.food.ordering.system.restaurant.service.dto.RestaurantApprovalRequest;
import com.food.ordering.system.restaurant.service.helper.RestaurantApprovalRequestHelper;
import com.food.ordering.system.restaurant.service.ports.input.message.listener.RestaurantApprovalRequestMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantApprovalRequestMessageListenerImpl implements RestaurantApprovalRequestMessageListener {

    private final RestaurantApprovalRequestHelper restaurantApprovalRequestHelper;

    @Override
    public void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest) {
        OrderApprovalEvent orderApprovalEvent =
                restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest);
        orderApprovalEvent.fire();
    }
}

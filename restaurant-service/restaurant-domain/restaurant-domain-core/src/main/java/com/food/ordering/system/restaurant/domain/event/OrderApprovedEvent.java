package com.food.ordering.system.restaurant.domain.event;

import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.domain.entity.OrderApproval;

import java.time.ZonedDateTime;
import java.util.List;


public class OrderApprovedEvent extends OrderApprovalEvent{

    public OrderApprovedEvent(OrderApproval orderApproval,
                              RestaurantId restaurantId, List<String> failureMessages,
                              ZonedDateTime createdAt) {
        super(orderApproval, restaurantId, failureMessages, createdAt);
    }

}

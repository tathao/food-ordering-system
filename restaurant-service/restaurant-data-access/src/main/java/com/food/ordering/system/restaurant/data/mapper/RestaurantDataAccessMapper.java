package com.food.ordering.system.restaurant.data.mapper;

import com.food.ordering.system.common.domain.valueobject.OrderId;
import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.data.entity.OrderApprovalEntity;
import com.food.ordering.system.restaurant.data.entity.RestaurantEntity;
import com.food.ordering.system.restaurant.domain.entity.OrderApproval;
import com.food.ordering.system.restaurant.domain.valuobject.OrderApprovalId;
import org.springframework.stereotype.Component;

@Component
public class RestaurantDataAccessMapper {

    public OrderApproval orderApprovalEntityToOrderApproval(OrderApprovalEntity orderApprovalEntity) {
        return OrderApproval.builder()
                .id(new OrderApprovalId(orderApprovalEntity.getId()))
                .restaurantId(new RestaurantId(orderApprovalEntity.getRestaurant().getRestaurantId()))
                .orderId(new OrderId(orderApprovalEntity.getOrderId()))
                .orderApprovalStatus(orderApprovalEntity.getStatus())
                .build();
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return OrderApprovalEntity.builder()
                .id(orderApproval.getId().getValue())
                .orderId(orderApproval.getOrderId().getValue())
                .status(orderApproval.getOrderApprovalStatus())
                .build();
    }
}

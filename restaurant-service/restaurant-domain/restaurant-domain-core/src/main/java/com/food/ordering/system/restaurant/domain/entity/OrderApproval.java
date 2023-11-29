package com.food.ordering.system.restaurant.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.OrderApprovalStatus;
import com.food.ordering.system.common.domain.valueobject.OrderId;
import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.domain.valueobject.OrderApprovalId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class OrderApproval extends BaseEntity<OrderApprovalId> {
    private final RestaurantId restaurantId;
    private final OrderId orderId;
    private final OrderApprovalStatus orderApprovalStatus;

    private OrderApproval(OrderApprovalBuilder builder) {
        setId(builder.id);
        this.restaurantId = builder.restaurantId;
        this.orderId = builder.orderId;
        this.orderApprovalStatus = builder.orderApprovalStatus;
    }

    public static OrderApprovalBuilder builder () {
        return new OrderApprovalBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class OrderApprovalBuilder {
        private OrderApprovalId id;
        private RestaurantId restaurantId;
        private OrderId orderId;
        private OrderApprovalStatus orderApprovalStatus;

        public OrderApproval build() {
            return new OrderApproval(this);
        }
    }
}

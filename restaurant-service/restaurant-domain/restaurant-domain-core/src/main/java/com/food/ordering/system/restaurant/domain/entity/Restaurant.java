package com.food.ordering.system.restaurant.domain.entity;

import com.food.ordering.system.common.domain.entity.AggregateRoot;
import com.food.ordering.system.common.domain.valueobject.OrderApprovalStatus;
import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.domain.valuobject.OrderApprovalId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Getter
public class Restaurant extends AggregateRoot<RestaurantId> {

    private OrderApproval orderApproval;
    @Setter
    private boolean active;
    private final OrderDetail orderDetail;

    public void validateOrder(List<String> failureMessages) {
        if (orderDetail.getOrderStatus() != OrderStatus.PAID) {
            failureMessages.add("Payment is not complete for order: " + orderDetail.getId().getValue());
        }
    }

    public void constructOrderApproval(OrderApprovalStatus orderApprovalStatus) {
        this.orderApproval = OrderApproval.builder()
                .id(new OrderApprovalId(UUID.randomUUID()))
                .restaurantId(this.getId())
                .orderId(this.getOrderDetail().getId())
                .orderApprovalStatus(orderApprovalStatus)
                .build();
    }

    private Restaurant(RestaurantBuilder builder) {
        setId(builder.id);
        this.orderApproval = builder.orderApproval;
        this.active = builder.active;
        this.orderDetail = builder.orderDetail;
    }

    public static RestaurantBuilder builder() {
        return new RestaurantBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class RestaurantBuilder {
        private RestaurantId id;
        private OrderApproval orderApproval;
        private boolean active;
        private OrderDetail orderDetail;

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}

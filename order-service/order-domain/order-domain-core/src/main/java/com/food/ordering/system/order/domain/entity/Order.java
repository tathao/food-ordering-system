package com.food.ordering.system.order.domain.entity;

import com.food.ordering.system.common.domain.entity.AggregateRoot;
import com.food.ordering.system.common.domain.valueobject.*;
import com.food.ordering.system.order.domain.exception.OrderDomainException;
import com.food.ordering.system.order.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.domain.valueobject.TrackingId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Getter
public class Order extends AggregateRoot<OrderId> {

    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final List<OrderItem> items;
    @Setter
    private  Money totalAmount;
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;
    public static final String FAILURE_MESSAGE_DELIMITER = ",";

    private Order(final OrderBuilder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
        totalAmount = builder.totalAmount;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public void validateOrder() {
        if (orderStatus != null || getId() != null) {
            throw new OrderDomainException("Order is not in correct state for initialization!");
        }
    }

    public void initializeOrder(long theLastOfOrderItemId) {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems(theLastOfOrderItemId);
        calculationTotalAmount();
    }

    private void calculationTotalAmount() {
        this.totalAmount.add(items.stream().map(OrderItem::getAmount)
                .reduce(Money.ZERO, Money::add));
    }

    private void initializeOrderItems(long theLastOfOrderItemId) {
        ++theLastOfOrderItemId;
        for (OrderItem orderItem: items) {
            orderItem.initializeOrderItem(super.getId(), new OrderItemId(theLastOfOrderItemId++));
        }
    }

    @Setter
    @Accessors(fluent = true)
    public static final class OrderBuilder {
        private OrderId orderId;
        private Money totalAmount;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        public Order build() {
            return new Order(this);
        }
    }
}

package com.food.ordering.system.order.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.OrderId;
import com.food.ordering.system.order.domain.valueobject.OrderItemId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class OrderItem extends BaseEntity<OrderItemId> {

    private OrderId orderId;
    private final int quantity;
    private Product product;
    private  Money amount;

    private OrderItem(final OrderItemBuilder builder) {
        super.setId(builder.orderItemId);
        product = builder.product;
        quantity = builder.quantity;
        amount = builder.amount;
    }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    void initializeOrderItem(final OrderId id, final OrderItemId orderItemId, Product product) {
        this.orderId = id;
        super.setId(orderItemId);
        this.product = product;
        calculationAmount();
    }

    private void calculationAmount() {
        this.amount= product.getPrice().multiply(quantity);
    }

    @Setter
    @Accessors(fluent = true)
    public static final class OrderItemBuilder {
        private OrderItemId orderItemId;
        private int quantity;
        private Product product;
        private Money amount;

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}

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
    private final Money price;
    private final Money subTotal;

    private OrderItem(final OrderItemBuilder builder) {
        super.setId(builder.orderItemId);
        product = builder.product;
        quantity = builder.quantity;
        subTotal = builder.subTotal;
        price = builder.price;
    }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    void initializeOrderItem(final OrderId id, final OrderItemId orderItemId) {
        this.orderId = id;
        super.setId(orderItemId);
    }

    boolean isPriceValid() {
        return price.isGreaterThanZero() &&
                price.equals(product.getPrice()) &&
                        price.multiply(quantity).equals(subTotal);
    }

    @Setter
    @Accessors(fluent = true)
    public static final class OrderItemBuilder {
        private OrderItemId orderItemId;
        private int quantity;
        private Product product;
        private Money price;
        private Money subTotal;

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}

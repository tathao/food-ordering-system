package com.food.ordering.system.restaurant.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.OrderId;
import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
public class OrderDetail extends BaseEntity<OrderId> {

    private OrderStatus orderStatus;
    private Money totalAmount;
    private final List<Product> products;

    private OrderDetail(OrderDetailBuilder builder) {
        setId(builder.id);
        this.orderStatus = builder.orderStatus;
        this.totalAmount = builder.totalAmount;
        this.products = builder.products;
    }

    public static OrderDetailBuilder builder() {
        return new OrderDetailBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class OrderDetailBuilder {
        private OrderId id;
        private OrderStatus orderStatus;
        private Money totalAmount;
        private List<Product> products;

        public OrderDetail build() {
            return new OrderDetail(this);
        }
    }
}

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
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public void initializeOrder(long theLastOfOrderItemId, final List<Product> products) {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems(theLastOfOrderItemId, products);
        calculationTotalAmount();
    }

    public void pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not in correct state for pay operation!");
        }
        orderStatus = OrderStatus.PAID;
    }

    private void calculationTotalAmount() {
        this.totalAmount =items.stream().map(OrderItem::getAmount)
                .reduce(Money.ZERO, Money::add);
    }

    private void initializeOrderItems(long theLastOfOrderItemId, List<Product> products) {
        AtomicLong idx = new AtomicLong(theLastOfOrderItemId + 1);
        Map<ProductId, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        items.forEach(orderItem -> {
            Product itemProduct = orderItem.getProduct();
            if (itemProduct != null) {
                ProductId productId = itemProduct.getId();
                if (productId != null && productMap.containsKey(productId)) {
                    orderItem.initializeOrderItem(super.getId(), new OrderItemId(idx.getAndIncrement()), productMap.get(productId));
                }
            }
        });
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

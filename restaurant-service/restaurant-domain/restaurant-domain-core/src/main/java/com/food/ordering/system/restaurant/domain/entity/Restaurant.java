package com.food.ordering.system.restaurant.domain.entity;

import com.food.ordering.system.common.domain.entity.AggregateRoot;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.OrderApprovalStatus;
import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.domain.valueobject.OrderApprovalId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class Restaurant extends AggregateRoot<RestaurantId> {

    private OrderApproval orderApproval;
    @Setter
    private boolean active;
    private final OrderDetail orderDetail;
    private List<Stock> stocks;

    public void validateOrder(List<String> failureMessages) {
        if (orderDetail.getOrderStatus() != OrderStatus.PAID) {
            failureMessages.add("Payment is not completed for order: "
            + orderDetail.getId().getValue());
        }
        validateStock(failureMessages);
        validateItemPrice(failureMessages);
    }

    public void constructOrderApproval(OrderApprovalStatus orderApprovalStatus) {
        this.orderApproval = OrderApproval.builder()
                .id(new OrderApprovalId(UUID.randomUUID()))
                .restaurantId(this.getId())
                .orderId(this.orderDetail.getId())
                .orderApprovalStatus(orderApprovalStatus)
                .build();
    }

    private void validateStock(List<String> failureMessages) {
        stocks.forEach(stock -> {
            List<Product> products = orderDetail.getProducts();
            failureMessages.addAll(products.stream()
                    .filter(product -> stock.getId().equals(product.getId()))
                    .map(stock::validateItemPrice)
                    .filter(message -> !message.isEmpty())
                    .toList());
        });
    }

    private void validateItemPrice(List<String> failureMessages) {
        Money totalAmount = orderDetail.getProducts().stream().map(product -> {
            return product.getPrice().multiply(product.getQuantity());
                }).reduce(Money.ZERO, Money::add);
        if (!totalAmount.equals(orderDetail.getTotalAmount())) {
            failureMessages.add("Price total is not correct for order: " + orderDetail.getId());
        }
    }

    private Restaurant(RestaurantBuilder builder) {
        setId(builder.id);
        this.orderApproval = builder.orderApproval;
        this.active = builder.active;
        this.orderDetail = builder.orderDetail;
        this.stocks = builder.stocks;
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
        private List<Stock> stocks;

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}

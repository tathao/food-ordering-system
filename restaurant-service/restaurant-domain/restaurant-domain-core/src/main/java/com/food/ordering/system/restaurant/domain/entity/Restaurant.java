package com.food.ordering.system.restaurant.domain.entity;

import com.food.ordering.system.common.domain.entity.AggregateRoot;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.OrderApprovalStatus;
import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.domain.exception.RestaurantDomainException;
import com.food.ordering.system.restaurant.domain.valueobject.OrderApprovalId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class Restaurant extends AggregateRoot<RestaurantId> {
    private OrderApproval orderApproval;
    @Setter
    private boolean active;
    private final OrderDetail orderDetail;
    @Setter
    private List<Product> products;
    public void validateOrder(List<String> failureMessages) {
        if (validatePaymentStatus(failureMessages)) {
            return;
        }
        List<Product> validatedProducts = validateProducts(failureMessages);
        if (validateTotalAmount(failureMessages, validatedProducts)) {
            return;
        }
    }

    private boolean validatePaymentStatus(List<String> failureMessages) {
        if (orderDetail.getOrderStatus() != OrderStatus.PAID) {
            failureMessages.add("Payment is not completed for order: " + orderDetail.getId().getValue());
            return true;
        }
        return false;
    }

    public void constructOrderApproval(OrderApprovalStatus orderApprovalStatus) {
        this.orderApproval = OrderApproval.builder()
                .id(new OrderApprovalId(UUID.randomUUID()))
                .restaurantId(this.getId())
                .orderId(this.getOrderDetail().getId())
                .orderApprovalStatus(orderApprovalStatus)
                .build();
    }

    private List<Product> validateProducts(List<String> failureMessages) {
        return products.stream()
                .flatMap(product -> orderDetail.getProducts().stream()
                        .filter(p -> product.getId().getValue().equals(p.getId().getValue()) && product.isAvailable())
                        .peek(p -> {
                            if (validateProductQuantity(failureMessages, product, p)) {
                                return;
                            }
                            p.setPrice(product.getPrice());
                            updateProductQuantities(product, p);
                        }))
                .toList();
    }

    private boolean validateProductQuantity(List<String> failureMessages, Product product, Product p) {
        product.setQuantityTemp(p.getQuantity());
        if (product.getQuantity() < p.getQuantity()) {
            failureMessages.add("The product quantity of restaurant hasn't enough!");
            return true;
        }
        p.setAvailable(product.isAvailable());
        return false;
    }

    private void updateProductQuantities(Product product, Product p) {
        product.setQuantity(product.getQuantity() - p.getQuantity());
    }

    private boolean validateTotalAmount(List<String> failureMessages, List<Product> validatedProducts) {
        Money totalAmount = validatedProducts.stream()
                .map(product -> {
                    if (validateProductAvailability(failureMessages, product)) {
                        return Money.ZERO;
                    }
                    return product.getPrice().multiply(product.getQuantity());
                })
                .reduce(Money.ZERO, Money::add);

        if (!totalAmount.equals(orderDetail.getTotalAmount())) {
            failureMessages.add("Price total is not correct for order: " + orderDetail.getId());
            return true;
        }

        return false;
    }

    private boolean validateProductAvailability(List<String> failureMessages, Product product) {
        if (!product.isAvailable()) {
            failureMessages.add("Product with id: " + product.getId().getValue() + " is not available");
            return true;
        }
        return false;
    }

    private Restaurant(RestaurantBuilder builder) {
        setId(builder.id);
        this.orderDetail = builder.orderDetail;
        this.active = builder.active;
        this.orderApproval = builder.orderApproval;
        this.products = builder.products;
    }

    public static RestaurantBuilder builder() {
        return new RestaurantBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class RestaurantBuilder {
        private RestaurantId id;
        private OrderDetail orderDetail;
        private OrderApproval orderApproval;
        private boolean active;
        private List<Product> products;

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}

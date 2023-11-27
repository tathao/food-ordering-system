package com.food.ordering.system.restaurant.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.ProductId;
import com.food.ordering.system.restaurant.domain.exception.RestaurantDomainException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class Stock extends BaseEntity<ProductId> {
    private final String name;
    private Money price;
    private boolean available;
    private int quantity;

    String validateItemPrice(Product product) {
        final Money itemPrice = price.multiply(quantity);
        if (!(this.price.equals(product.getPrice()) && itemPrice.equals(product.getPrice().multiply(product.getQuantity()))))
            return "The items price is not equal the stock price!";
        if (this.quantity < product.getQuantity()) {
            return "The stock doesn't have enough the quantity!";
        }
        if (!available)
            return "The Product with id: " + builder().id.getValue() + " is not available";
        this.quantity -= product.getQuantity();
        return "";
    }

    private Stock(StockBuilder builder) {
        setId(builder.id);
        this.name = builder.name;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.available = builder.available;
    }

    public static StockBuilder builder() {
        return new StockBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class StockBuilder {
        private ProductId id;
        private String name;
        private Money price;
        private boolean available;
        private int quantity;

        public Stock build() {
            return new Stock(this);
        }
    }
}

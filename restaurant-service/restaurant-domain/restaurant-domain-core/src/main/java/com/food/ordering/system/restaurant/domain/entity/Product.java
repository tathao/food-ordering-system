package com.food.ordering.system.restaurant.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;
    private int quantity;
    private int quantityTemp;
    private boolean available;

    private Product(ProductBuilder builder) {
        setId(builder.id);
        this.available = builder.available;
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class ProductBuilder {
        private ProductId id;
        private String name;
        private Money price;
        private int quantity;
        private boolean available;

        public Product build() {
            return new Product(this);
        }
    }
}

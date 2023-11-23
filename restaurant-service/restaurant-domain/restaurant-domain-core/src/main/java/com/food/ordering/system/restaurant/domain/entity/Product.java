package com.food.ordering.system.restaurant.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class Product extends BaseEntity<ProductId> {
    private String name;
    private int quantity;
    private boolean available;

    private Product(ProductBuilder builder) {
        setId(builder.id);
        this.name = builder.name;
        this.quantity = builder.quantity;
        this.available = builder.available;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class ProductBuilder {
        private ProductId id;
        private String name;
        private int quantity;
        private boolean available;

        public Product build() {
            return new Product(this);
        }
    }
}

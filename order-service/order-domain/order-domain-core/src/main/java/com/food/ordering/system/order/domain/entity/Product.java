package com.food.ordering.system.order.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class Product extends BaseEntity<ProductId> {
    public String name;
    public Money price;

    public Product(final ProductId productId) {
        super.setId(productId);
    }

    private Product(final ProductBuilder builder) {
        super.setId(builder.productId);
        name = builder.name;
        price = builder.price;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class ProductBuilder {
        private ProductId productId;
        private String name;
        private Money price;
        public Product build() {
            return new Product(this);
        }
    }
}

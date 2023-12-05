package com.food.ordering.system.order.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
public class Customer extends BaseEntity<CustomerId> {

    private final String userName;
    private final String firstName;
    private final String lastName;
    @Setter
    private Money amount;
    @Setter
    private boolean active;

    private Customer(CustomerBuilder builder) {
        setId(builder.id);
        this.userName = builder.userName;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.active = builder.active;
        this.amount = builder.amount;
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    @Setter
    @Accessors(fluent = true)
    public static final class CustomerBuilder {
        private CustomerId id;
        private String userName;
        private String firstName;
        private String lastName;
        private Money amount;
        private boolean active;

        public Customer build() {
            return new Customer(this);
        }
    }
}

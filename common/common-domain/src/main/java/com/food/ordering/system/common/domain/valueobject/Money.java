package com.food.ordering.system.common.domain.valueobject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Money {

    private final BigDecimal amount;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public boolean isGreaterThanZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(final Money money) {
        return amount != null && amount.compareTo(money.getAmount()) > 0;
    }

    public Money add(final Money money) {
        return new Money(setScale(amount.add(money.getAmount())));
    }

    public Money subtract(final Money money) {
        return new Money(setScale(amount.subtract(money.getAmount())));
    }

    public Money multiply(final int multiplier) {
        return new Money(setScale(amount.multiply(new BigDecimal(multiplier))));
    }

    private BigDecimal setScale(final BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}

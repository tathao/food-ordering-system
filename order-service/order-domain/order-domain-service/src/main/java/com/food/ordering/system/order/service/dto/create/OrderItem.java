package com.food.ordering.system.order.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {

    @NotNull
    private final UUID productId;
    @NotNull
    private final Integer quantity;
}

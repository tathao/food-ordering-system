package com.food.ordering.system.order.db.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderItemIdEntity implements Serializable {

    private Long id;
    private OrderEntity orderEntity;
}

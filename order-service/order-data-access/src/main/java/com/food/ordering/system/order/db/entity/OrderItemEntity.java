package com.food.ordering.system.order.db.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
@IdClass(OrderItemIdEntity.class)
public class OrderItemEntity {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "amount")
    private BigDecimal subTotal;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}

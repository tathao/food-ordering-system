package com.food.ordering.system.order.db.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "order_product")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderEntity;
}

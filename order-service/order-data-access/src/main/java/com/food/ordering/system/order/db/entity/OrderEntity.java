package com.food.ordering.system.order.db.entity;

import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@DynamicUpdate
public class OrderEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "customer_id")
    private UUID customerId;
    @Column(name = "restaurant_id")
    private UUID restaurantId;
    @Column(name = "tracking_id")
    private UUID trackingId;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "failure_messages")
    private String failureMessage;

    @OneToOne(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private AddressEntity address;
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems;
}

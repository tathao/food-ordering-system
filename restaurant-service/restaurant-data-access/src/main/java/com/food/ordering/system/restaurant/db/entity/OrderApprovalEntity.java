package com.food.ordering.system.restaurant.db.entity;

import com.food.ordering.system.common.domain.valueobject.OrderApprovalStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_approval")
@Entity
public class OrderApprovalEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "order_id")
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    @Column(name = "approoval_status")
    private OrderApprovalStatus orderApprovalStatus;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;
}

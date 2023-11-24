package com.food.ordering.system.restaurant.data.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "restaurant")
@IdClass(RestaurantEntityId.class)
@EqualsAndHashCode(of = {"restaurantId", "productId"})
public class RestaurantEntity {

    @Id
    @Column(name = "id")
    private UUID restaurantId;
    @Id
    @Column(name = "product_id")
    private UUID productId;
    @Column(name = "restaurant_name")
    private String restaurantName;
    @Column(name = "restaurant_active")
    private Boolean restaurantActive;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_price")
    private BigDecimal productPrice;
    @Column(name = "prodcut_available")
    private Boolean productAvailable;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<OrderApprovalEntity> orderApprovals;
}

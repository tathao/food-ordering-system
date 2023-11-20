package com.food.ordering.system.order.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_address")
public class AddressEntity {

    @Id
    private UUID id;
    @Column(name = "street")
    private String street;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "city")
    private String city;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
}

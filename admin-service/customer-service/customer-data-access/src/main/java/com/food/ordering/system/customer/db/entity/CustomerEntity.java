package com.food.ordering.system.customer.db.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer", schema = "customer")
@Entity
public class CustomerEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "active")
    private boolean active;
}

package com.food.ordering.system.restaurant.db.entity;

import com.food.ordering.system.restaurant.domain.entity.Product;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Table(name = "restaurant")
@Entity
public class RestaurantEntity {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<StockEntity> products;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<OrderApprovalEntity> orderApprovalEntities;

    public void updateStock(List<Product> productList) {
        products.forEach(productEntity ->
                productList.stream()
                        .filter(product -> productEntity.getId().equals(product.getId().getValue()) && product.getQuantityTemp()!=0)
                        .findFirst()
                        .ifPresent(product -> productEntity.setQuantity(product.getQuantity())));
    }
    public void addOrderApproval(OrderApprovalEntity orderApproval) {
        orderApproval.setRestaurant(this);
        this.orderApprovalEntities.add(orderApproval);
    }
}

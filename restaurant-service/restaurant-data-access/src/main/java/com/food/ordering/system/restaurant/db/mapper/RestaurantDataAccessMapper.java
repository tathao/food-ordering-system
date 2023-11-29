package com.food.ordering.system.restaurant.db.mapper;

import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.ProductId;
import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.db.entity.OrderApprovalEntity;
import com.food.ordering.system.restaurant.db.entity.RestaurantEntity;
import com.food.ordering.system.restaurant.db.entity.StockEntity;
import com.food.ordering.system.restaurant.domain.entity.OrderApproval;
import com.food.ordering.system.restaurant.domain.entity.Product;
import com.food.ordering.system.restaurant.domain.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public Product stockEntityToStock(StockEntity stockEntity) {
        return Product.builder()
                .id(new ProductId(stockEntity.getId()))
                .name(stockEntity.getName())
                .quantity(stockEntity.getQuantity())
                .price(new Money(stockEntity.getPrice()))
                .available(stockEntity.isAvailable())
                .build();
    }

    public Restaurant restaurantEntityToRestaurant(RestaurantEntity restaurantEntity) {
        return Restaurant.builder()
                .active(restaurantEntity.getActive())
                .products(restaurantEntity.getProducts().stream().map(this::stockEntityToStock).collect(Collectors.toList()))
                .build();
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return OrderApprovalEntity.builder()
                .id(orderApproval.getOrderId().getValue())
                .orderId(orderApproval.getOrderId().getValue())
                .orderApprovalStatus(orderApproval.getOrderApprovalStatus())
                .build();
    }
}

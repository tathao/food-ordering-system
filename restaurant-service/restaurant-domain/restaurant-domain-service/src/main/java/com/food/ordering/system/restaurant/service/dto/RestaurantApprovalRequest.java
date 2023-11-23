package com.food.ordering.system.restaurant.service.dto;

import com.food.ordering.system.common.domain.valueobject.RestaurantOrderStatus;
import com.food.ordering.system.restaurant.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantApprovalRequest {
    private String id;
    private String sagaId;
    private String restaurantId;
    private String orderId;
    private RestaurantOrderStatus restaurantOrderStatus;
    private List<Product> products;
    private java.time.Instant createdAt;
}

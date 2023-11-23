package com.food.ordering.system.restaurant.service;

import com.food.ordering.system.restaurant.service.dto.RestaurantApprovalRequest;

public interface RestaurantDomainApplicationService {

    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}

package com.food.ordering.system.restaurant.service.impl;

import com.food.ordering.system.restaurant.service.RestaurantDomainApplicationService;
import com.food.ordering.system.restaurant.service.dto.RestaurantApprovalRequest;
import com.food.ordering.system.restaurant.service.ports.input.message.listener.RestaurantApprovalRequestMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantDomainApplicationServiceImpl implements RestaurantDomainApplicationService {

    private final RestaurantApprovalRequestMessageListener restaurantApprovalRequestMessageListener;

    @Override
    public void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest) {
        restaurantApprovalRequestMessageListener.approveOrder(restaurantApprovalRequest);
    }
}

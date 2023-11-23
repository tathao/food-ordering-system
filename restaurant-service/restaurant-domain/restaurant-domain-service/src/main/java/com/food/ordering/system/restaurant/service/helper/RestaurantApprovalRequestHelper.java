package com.food.ordering.system.restaurant.service.helper;

import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.domain.RestaurantDomainService;
import com.food.ordering.system.restaurant.domain.entity.Restaurant;
import com.food.ordering.system.restaurant.domain.event.OrderApprovalEvent;
import com.food.ordering.system.restaurant.domain.exception.RestaurantNotFoundException;
import com.food.ordering.system.restaurant.service.dto.RestaurantApprovalRequest;
import com.food.ordering.system.restaurant.service.ports.output.message.publisher.OrderApprovedMessagePublisher;
import com.food.ordering.system.restaurant.service.ports.output.message.publisher.OrderRejectedMessagePublisher;
import com.food.ordering.system.restaurant.service.ports.output.repository.OrderApprovalRepository;
import com.food.ordering.system.restaurant.service.ports.output.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantApprovalRequestHelper {

    private final RestaurantRepository restaurantRepository;
    private final OrderApprovalRepository orderApprovalRepository;
    private final RestaurantDomainService restaurantDomainService;
    private final OrderApprovedMessagePublisher orderApprovedMessagePublisher;
    private final OrderRejectedMessagePublisher orderRejectedMessagePublisher;

    @Transactional
    public OrderApprovalEvent persistOrderApproval(RestaurantApprovalRequest restaurantApprovalRequest) {
        log.info("Processing restaurant approval for order id: {}", restaurantApprovalRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        Restaurant restaurant = findRestaurant(restaurantApprovalRequest.getRestaurantId());
        OrderApprovalEvent orderApprovalEvent =
                restaurantDomainService.validateOrder(
                        restaurant,
                        failureMessages,
                        orderApprovedMessagePublisher,
                        orderRejectedMessagePublisher);
        orderApprovalRepository.save(restaurant.getOrderApproval());
        return orderApprovalEvent;
    }

    private Restaurant findRestaurant(String restaurantId) {
        return restaurantRepository.findRestaurantById(new RestaurantId(UUID.fromString(restaurantId)))
                .orElseThrow(() -> {
                    log.error("Restaurant with id {} not found!", restaurantId);
                    return new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found!");
                });
    }
}

package com.food.ordering.system.restaurant.service.helper;

import com.food.ordering.system.common.domain.valueobject.OrderId;
import com.food.ordering.system.restaurant.domain.RestaurantDomainService;
import com.food.ordering.system.restaurant.domain.entity.Restaurant;
import com.food.ordering.system.restaurant.domain.event.OrderApprovalEvent;
import com.food.ordering.system.restaurant.domain.exception.RestaurantDomainException;
import com.food.ordering.system.restaurant.domain.exception.RestaurantNotFoundException;
import com.food.ordering.system.restaurant.service.dto.RestaurantApprovalRequest;
import com.food.ordering.system.restaurant.service.mapper.RestaurantDataMapper;
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

    private final RestaurantDomainService restaurantDomainService;
    private final RestaurantRepository restaurantRepository;
    private final OrderApprovalRepository orderApprovalRepository;
    private final OrderApprovedMessagePublisher orderApprovedMessagePublisher;
    private final OrderRejectedMessagePublisher orderRejectedMessagePublisher;
    private final RestaurantDataMapper restaurantDataMapper;


    @Transactional
    public OrderApprovalEvent persistOrderApproval(RestaurantApprovalRequest restaurantApprovalRequest) {
        log.info("Processing restaurant approval for order id: {}", restaurantApprovalRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        Restaurant restaurant = findRestaurantById(restaurantApprovalRequest);
        OrderApprovalEvent orderApprovalEvent = restaurantDomainService.validateOrder(
                restaurant, failureMessages,
                orderApprovedMessagePublisher,
                orderRejectedMessagePublisher
        );
        orderApprovalRepository.save(restaurant.getOrderApproval());
        return orderApprovalEvent;
    }

    private Restaurant findRestaurantById(RestaurantApprovalRequest restaurantApprovalRequest) {
        Restaurant restaurant = restaurantDataMapper.restaurantApprovalRequestToRestaurant(restaurantApprovalRequest);
        Restaurant restaurantResult = restaurantRepository.findRestaurantById(restaurantApprovalRequest.getId())
                .orElseThrow(()->{
                    log.error("Restaurant with id " + restaurant.getId().getValue() + " not found!");
                    return new RestaurantNotFoundException("Restaurant with id " + restaurant.getId().getValue() + " not found1");
                });
        restaurant.setActive(restaurant.isActive());
        restaurant.getOrderDetail().setId(new OrderId(
                UUID.fromString(restaurantApprovalRequest.getOrderId())
        ));

        return restaurant;
    }
}

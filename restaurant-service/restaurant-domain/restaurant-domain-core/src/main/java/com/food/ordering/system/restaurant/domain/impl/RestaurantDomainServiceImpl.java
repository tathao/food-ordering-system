package com.food.ordering.system.restaurant.domain.impl;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.common.domain.valueobject.OrderApprovalStatus;
import com.food.ordering.system.restaurant.domain.RestaurantDomainService;
import com.food.ordering.system.restaurant.domain.entity.Restaurant;
import com.food.ordering.system.restaurant.domain.event.OrderApprovalEvent;
import com.food.ordering.system.restaurant.domain.event.OrderApprovedEvent;
import com.food.ordering.system.restaurant.domain.event.OrderRejectedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.food.ordering.system.common.domain.constant.DomainConstant.UTC;

@Slf4j
public class RestaurantDomainServiceImpl implements RestaurantDomainService {

    @Override
    public OrderApprovalEvent validateOrder(Restaurant restaurant, List<String> failureMessages,
                                            DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher,
                                            DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher) {
        restaurant.validateOrder(failureMessages);
        log.info("Validating order with id: {}", restaurant.getOrderDetail().getId().getValue());
        if (failureMessages.isEmpty()) {
            log.info("Order is approved for order id: {}", restaurant.getOrderDetail().getId().getValue());
            restaurant.constructOrderApproval(OrderApprovalStatus.APPROVED);
            return new OrderApprovedEvent(restaurant.getOrderApproval(),
                    restaurant.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of(UTC)),
                    orderApprovedEventDomainEventPublisher);
        }
        log.info("Order is rejected for order id: {}", restaurant.getOrderDetail().getId().getValue());
        restaurant.constructOrderApproval(OrderApprovalStatus.REJECTED);
        return new OrderRejectedEvent(restaurant.getOrderApproval(),
                restaurant.getId(),
                failureMessages,
                ZonedDateTime.now(ZoneId.of(UTC)),
                orderRejectedEventDomainEventPublisher);
    }
}

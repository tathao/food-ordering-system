package com.food.ordering.system.order.service.helper;

import com.food.ordering.system.order.domain.OrderDomainService;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.service.ports.output.message.publisher.payment.OrderApprovalRestaurantMessagePublisher;
import com.food.ordering.system.order.service.ports.output.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderApprovalHelper {
    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;
    private final OrderApprovalRestaurantMessagePublisher orderApprovalRestaurantMessagePublisher;

    @Transactional
    public OrderPaidEvent persistOrderToRestaurant(PaymentResponse paymentResponse) {
        Order order = orderRepository.findById(UUID.fromString(paymentResponse.getOrderId()))
                .orElseThrow(()-> new OrderNotFoundException("Could not find order with id: "
                + paymentResponse.getOrderId()));
        OrderPaidEvent orderPaidEvent = orderDomainService.validateAndApprovalTheOrder(order,
                orderApprovalRestaurantMessagePublisher);
        orderRepository.saveOrder(order);
        log.info("Order is persisted with id: {}", orderPaidEvent.getOrder().getId().getValue());
        return orderPaidEvent;
    }

    @Transactional
    public void persistOrderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {
        Order order = orderRepository.findById(UUID.fromString(restaurantApprovalResponse.getOrderId()))
                .orElseThrow(()-> new OrderNotFoundException("Could not find order with id: "
                        + restaurantApprovalResponse.getOrderId()));
        orderDomainService.approveOrder(order);
        orderRepository.saveOrder(order);
        log.info("Order is persisted with id: {}", order.getId().getValue());
    }
}

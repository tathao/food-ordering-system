package com.food.ordering.system.order.service.helper;

import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.ports.output.repository.OrderRepository;
import com.food.ordering.system.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderSagaHelper {
    private final OrderRepository orderRepository;

    public Order findOrder(String orderId) {
        Optional<Order> orderResponse =  orderRepository.findById(UUID.fromString(orderId));
        if (orderResponse.isEmpty()) {
            log.error("Order with id: {} could not be found!", orderId);
            throw new OrderNotFoundException("Order with id " + orderId +
                    " could not be found!");
        }
        return orderResponse.get();
    }

    public void saveOrder(Order order) {
        orderRepository.saveOrder(order);
    }

    public SagaStatus orderStatusToSagaStatus(OrderStatus orderStatus) {
        switch (orderStatus) {
            case PAID -> {
                return SagaStatus.PROCESSING;
            }
            case APPROVED -> {
                return SagaStatus.SUCCEEDED;
            }
            case CANCELLING -> {
                return SagaStatus.COMPENSATING;
            }
            case CANCELLED -> {
                return SagaStatus.COMPENSATED;
            }
            default -> {
                return SagaStatus.STARTED;
            }
        }
    }
}

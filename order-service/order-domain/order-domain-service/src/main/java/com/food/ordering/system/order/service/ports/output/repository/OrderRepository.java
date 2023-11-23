package com.food.ordering.system.order.service.ports.output.repository;

import com.food.ordering.system.order.domain.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order saveOrder(Order order);

    Optional<Order> findById(UUID orderId);
}

package com.food.ordering.system.order.service.ports.output.repository;

import com.food.ordering.system.order.domain.entity.Order;

public interface OrderRepository {
    Order save(Order order);
}

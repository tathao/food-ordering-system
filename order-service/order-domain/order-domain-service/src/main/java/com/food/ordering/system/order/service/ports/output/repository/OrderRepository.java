package com.food.ordering.system.order.service.ports.output.repository;

import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.valueobject.TrackingId;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    void saveOrder(Order order);

    Optional<Order> findById(UUID orderId);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}

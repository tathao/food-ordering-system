package com.food.ordering.system.order.db.adapter;

import com.food.ordering.system.order.db.entity.OrderEntity;
import com.food.ordering.system.order.db.mapper.OrderDataAccessMapper;
import com.food.ordering.system.order.db.repository.OrderJpaRepository;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.exception.OrderDomainException;
import com.food.ordering.system.order.domain.valueobject.TrackingId;
import com.food.ordering.system.order.service.ports.output.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;
    @Override
    public void saveOrder(Order order) {
        Objects.requireNonNull(order, "Order must not be null");
        Objects.requireNonNull(orderJpaRepository, "OrderJpaRepository must not be null");
        Objects.requireNonNull(orderDataAccessMapper, "OrderDataAccessMapper must not be null");
        try {
            OrderEntity orderEntity = getOrCreateOrderEntity(order);
            Objects.requireNonNull(orderEntity, "OrderEntity must not be null");
            orderEntity.setOrderStatus(order.getOrderStatus());
            order = orderDataAccessMapper.orderEntityToOrder(orderJpaRepository.save(orderEntity));
            order = Optional.ofNullable(order)
                    .orElseThrow(() -> new OrderDomainException("Could not save order! Order is null after saving."));

            if (log.isDebugEnabled()) {
                log.debug("Order is saved with id: {}", order.getId().getValue());
            }

        } catch (Exception e) {
            log.error("An error occurred while saving the order", e);
            throw new OrderDomainException("An error occurred while saving the order", e);
        }
    }

    private OrderEntity getOrCreateOrderEntity(Order order) {
        return orderJpaRepository.findById(order.getId().getValue())
                .orElseGet(() -> orderDataAccessMapper.orderToOrderEntity(order));
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        Optional<OrderEntity> optionalOrderEntity = orderJpaRepository.findById(orderId);
        return optionalOrderEntity.map(orderDataAccessMapper::orderEntityToOrder);
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }
}

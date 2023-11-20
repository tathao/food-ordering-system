package com.food.ordering.system.order.db.adapter;

import com.food.ordering.system.order.db.entity.OrderEntity;
import com.food.ordering.system.order.db.mapper.OrderDataAccessMapper;
import com.food.ordering.system.order.db.repository.OrderJpaRepository;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.service.ports.output.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;
    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntityToOrder(
                orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order))
        );
    }
}

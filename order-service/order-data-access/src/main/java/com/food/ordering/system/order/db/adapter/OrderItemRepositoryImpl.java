package com.food.ordering.system.order.db.adapter;

import com.food.ordering.system.order.db.repository.OrderItemJpaRepository;
import com.food.ordering.system.order.service.ports.output.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final OrderItemJpaRepository orderItemJpaRepository;
    @Override
    public Long theLastOfOrderItemId() {
        return orderItemJpaRepository.theLastOfOrderItemId() != null ? orderItemJpaRepository.theLastOfOrderItemId() : 0L;
    }
}

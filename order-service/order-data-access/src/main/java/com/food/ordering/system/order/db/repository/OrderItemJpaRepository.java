package com.food.ordering.system.order.db.repository;

import com.food.ordering.system.order.db.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query("SELECT MAX(oItem.id) FROM OrderItemEntity oItem")
    Long theLastOfOrderItemId();
}

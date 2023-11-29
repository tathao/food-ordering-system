package com.food.ordering.system.restaurant.db.repository;

import com.food.ordering.system.restaurant.db.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, UUID> {
    Optional<List<StockEntity>> findStockEntitiesByAvailableTrueAndRestaurantId(UUID restaurantId);
    Optional<List<StockEntity>> findStockEntitiesByAvailableTrueAndRestaurantIdAndAndIdIn(UUID restaurantId, List<UUID>productIds);
}

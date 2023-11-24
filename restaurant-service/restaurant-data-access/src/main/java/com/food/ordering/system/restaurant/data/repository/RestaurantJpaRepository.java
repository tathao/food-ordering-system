package com.food.ordering.system.restaurant.data.repository;

import com.food.ordering.system.restaurant.data.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, UUID> {

    Optional<RestaurantEntity> findByRestaurantIdAndRestaurantActiveTrue(UUID restaurantId);
}

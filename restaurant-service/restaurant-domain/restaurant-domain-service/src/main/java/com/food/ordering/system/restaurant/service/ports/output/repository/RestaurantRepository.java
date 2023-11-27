package com.food.ordering.system.restaurant.service.ports.output.repository;

import com.food.ordering.system.restaurant.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantById(String restaurantId);
}

package com.food.ordering.system.restaurant.db.adapter;

import com.food.ordering.system.restaurant.db.entity.OrderApprovalEntity;
import com.food.ordering.system.restaurant.db.entity.RestaurantEntity;
import com.food.ordering.system.restaurant.db.entity.StockEntity;
import com.food.ordering.system.restaurant.db.mapper.RestaurantDataAccessMapper;
import com.food.ordering.system.restaurant.db.repository.RestaurantJpaRepository;
import com.food.ordering.system.restaurant.db.repository.StockJpaRepository;
import com.food.ordering.system.restaurant.domain.entity.Product;
import com.food.ordering.system.restaurant.domain.entity.Restaurant;
import com.food.ordering.system.restaurant.domain.exception.RestaurantDomainException;
import com.food.ordering.system.restaurant.service.ports.output.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {
    private final StockJpaRepository stockJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;
    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        Optional<RestaurantEntity> optionalRestaurantEntity = restaurantJpaRepository.findById(restaurant.getId().getValue());
        return optionalRestaurantEntity.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }

    @Override
    public void save(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantJpaRepository.findById(restaurant.getId().getValue())
                .orElseThrow(()-> new RestaurantDomainException("Cannot find the restaurant with id: "
                        + restaurant.getId().getValue()));
        restaurantEntity.updateStock(restaurant.getProducts());
        OrderApprovalEntity orderApprovalEntity = restaurantDataAccessMapper
                .orderApprovalToOrderApprovalEntity(restaurant.getOrderApproval());
        restaurantEntity.addOrderApproval(orderApprovalEntity);
        restaurantJpaRepository.save(restaurantEntity);
    }

}

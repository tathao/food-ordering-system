package com.food.ordering.system.restaurant.data.adapter;

import com.food.ordering.system.restaurant.data.entity.OrderApprovalEntity;
import com.food.ordering.system.restaurant.data.entity.RestaurantEntity;
import com.food.ordering.system.restaurant.data.exception.RestaurantDataAccessNotFoundException;
import com.food.ordering.system.restaurant.data.mapper.RestaurantDataAccessMapper;
import com.food.ordering.system.restaurant.data.repository.OrderApprovalJpaRepository;
import com.food.ordering.system.restaurant.data.repository.RestaurantJpaRepository;
import com.food.ordering.system.restaurant.domain.entity.OrderApproval;
import com.food.ordering.system.restaurant.service.ports.output.repository.OrderApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderApprovalRepositoryImpl implements OrderApprovalRepository {

    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    @Override
    public OrderApproval save(OrderApproval orderApproval) {
        UUID restaurantId = orderApproval.getRestaurantId().getValue();
        RestaurantEntity restaurantEntity = restaurantJpaRepository.findByRestaurantIdAndRestaurantActiveTrue(restaurantId)
                .orElseThrow(()->new RestaurantDataAccessNotFoundException("Cannot find the restaurant with id: "
                        + restaurantId));
        OrderApprovalEntity orderApprovalEntity = restaurantDataAccessMapper.orderApprovalToOrderApprovalEntity(orderApproval);
        orderApprovalEntity.setRestaurant(restaurantEntity);

        orderApprovalEntity = orderApprovalJpaRepository.save(orderApprovalEntity);
        return restaurantDataAccessMapper.orderApprovalEntityToOrderApproval(orderApprovalEntity);
    }
}

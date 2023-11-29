package com.food.ordering.system.restaurant.config;

import com.food.ordering.system.restaurant.domain.RestaurantDomainService;
import com.food.ordering.system.restaurant.domain.impl.RestaurantDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfig {

    @Bean
    public RestaurantDomainService restaurantDomainService() {
        return new RestaurantDomainServiceImpl();
    }
}

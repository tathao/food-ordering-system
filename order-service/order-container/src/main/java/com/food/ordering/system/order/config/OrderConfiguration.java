package com.food.ordering.system.order.config;

import com.food.ordering.system.order.domain.OrderDomainService;
import com.food.ordering.system.order.domain.impl.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}

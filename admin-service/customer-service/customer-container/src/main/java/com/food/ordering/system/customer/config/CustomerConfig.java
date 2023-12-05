package com.food.ordering.system.customer.config;

import com.food.ordering.system.order.domain.CustomerDomainService;
import com.food.ordering.system.order.domain.impl.CustomerDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {
    @Bean
    public CustomerDomainService customerDomainService() {
        return new CustomerDomainServiceImpl();
    }
}

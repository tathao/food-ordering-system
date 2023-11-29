package com.food.ordering.system.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.food.ordering.system.order.db")
@EnableJpaRepositories(basePackages = "com.food.ordering.system.order.db")
@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}

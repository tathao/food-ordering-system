package com.food.ordering.system.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.food.ordering.system.customer.db")
@EnableJpaRepositories(basePackages = "com.food.ordering.system.customer.db")
@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}

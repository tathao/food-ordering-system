package com.food.ordering.system.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.food.ordering.system.payment.db")
@EntityScan(basePackages = "com.food.ordering.system.payment.db")
@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }
}

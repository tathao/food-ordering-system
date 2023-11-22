package com.food.ordering.system.payment.config;

import com.food.ordering.system.payment.domain.PaymentDomainService;
import com.food.ordering.system.payment.domain.impl.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainServiceImpl();
    }
}

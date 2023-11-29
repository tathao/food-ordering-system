package com.food.ordering.system.payment.service.ports.output.repository;

import com.food.ordering.system.payment.domain.entity.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}

package com.food.ordering.system.payment.service.ports.output.repository;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.payment.domain.entity.Credit;

import java.util.Optional;

public interface CreditRepository {

    Optional<Credit> findByCustomerId(CustomerId customerId);
    Credit save(Credit credit);
}

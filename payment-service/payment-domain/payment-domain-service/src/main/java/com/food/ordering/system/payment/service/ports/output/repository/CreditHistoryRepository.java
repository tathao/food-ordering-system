package com.food.ordering.system.payment.service.ports.output.repository;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.payment.domain.entity.CreditHistory;

import java.util.List;
import java.util.Optional;

public interface CreditHistoryRepository {

    void save(CreditHistory creditHistory);
    Optional<List<CreditHistory>> findAllByCustomerId(CustomerId customerId);
}

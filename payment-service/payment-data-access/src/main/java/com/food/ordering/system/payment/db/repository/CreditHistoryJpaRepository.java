package com.food.ordering.system.payment.db.repository;

import com.food.ordering.system.payment.db.document.CreditHistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditHistoryJpaRepository extends MongoRepository<CreditHistoryEntity, UUID> {

    Optional<List<CreditHistoryEntity>> findAllByCustomerId(UUID customerId);
}

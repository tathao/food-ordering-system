package com.food.ordering.system.payment.db.repository;

import com.food.ordering.system.payment.db.document.CreditEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditJpaRepository extends MongoRepository<CreditEntity, UUID> {
    Optional<CreditEntity> findByCustomerId(UUID customerId);
}

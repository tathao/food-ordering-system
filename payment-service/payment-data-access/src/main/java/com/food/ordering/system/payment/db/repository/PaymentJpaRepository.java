package com.food.ordering.system.payment.db.repository;

import com.food.ordering.system.payment.db.document.PaymentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentJpaRepository extends MongoRepository<PaymentEntity, UUID> {
}

package com.food.ordering.system.payment.db.repository;

import com.food.ordering.system.payment.db.document.OrderOutboxEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderOutboxMongoRepository extends MongoRepository<OrderOutboxEntity, UUID> {

    Optional<List<OrderOutboxEntity>> findByTypeAndOutboxStatus(String type, String outboxStatus);
    Optional<OrderOutboxEntity> findByTypeAndSagaIdAndOutboxStatusAndPaymentStatus(
            String type, UUID sagaId, String outboxStatus, String paymentStatus
    );
    void deleteByTypeAndOutboxStatus(String type, String outboxStatus);
}

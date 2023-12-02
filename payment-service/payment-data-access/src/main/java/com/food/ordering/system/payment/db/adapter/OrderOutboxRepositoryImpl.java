package com.food.ordering.system.payment.db.adapter;

import com.food.ordering.system.common.domain.valueobject.PaymentStatus;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.payment.db.exception.OrderOutboxNotFoundException;
import com.food.ordering.system.payment.db.mapper.OrderOutboxDataAccessMapper;
import com.food.ordering.system.payment.db.repository.OrderOutboxMongoRepository;
import com.food.ordering.system.payment.service.outbox.model.OrderOutboxMessage;
import com.food.ordering.system.payment.service.ports.output.repository.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderOutboxRepositoryImpl implements OrderOutboxRepository {
    private final OrderOutboxMongoRepository orderOutboxMongoRepository;
    private final OrderOutboxDataAccessMapper orderOutboxDataAccessMapper;
    @Override
    public OrderOutboxMessage save(OrderOutboxMessage orderOutboxMessage) {

        return orderOutboxDataAccessMapper
                .orderOutboxEntityToOrderOutboxMessage(orderOutboxMongoRepository
                        .save(orderOutboxDataAccessMapper
                                .orderOutboxMessageToOrderOutboxEntity(orderOutboxMessage)));
    }

    @Override
    public Optional<List<OrderOutboxMessage>> findByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus) {
        return Optional.of(
                orderOutboxMongoRepository
                        .findByTypeAndOutboxStatus(type, outboxStatus.name())
                        .orElseThrow(()-> new OrderOutboxNotFoundException("Approval outbox object "
                        + " cannot be found for saga type " + type))
                        .stream()
                        .map(orderOutboxDataAccessMapper::orderOutboxEntityToOrderOutboxMessage)
                        .collect(Collectors.toList()));
    }

    @Override
    public Optional<OrderOutboxMessage> findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(String type, UUID sagaId,
                                                                                           PaymentStatus paymentStatus, OutboxStatus outboxStatus) {
        return orderOutboxMongoRepository.findByTypeAndSagaIdAndOutboxStatusAndPaymentStatus(
                type, sagaId, paymentStatus.name(), outboxStatus.name()
        ).map(orderOutboxDataAccessMapper::orderOutboxEntityToOrderOutboxMessage);
    }

    @Override
    public void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus) {
        orderOutboxMongoRepository.deleteByTypeAndOutboxStatus(type, outboxStatus.name());
    }
}

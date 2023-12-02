package com.food.ordering.system.payment.db.mapper;

import com.food.ordering.system.common.domain.valueobject.PaymentStatus;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.payment.db.document.OrderOutboxEntity;
import com.food.ordering.system.payment.service.outbox.model.OrderOutboxMessage;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static com.food.ordering.system.common.domain.constant.DomainConstant.UTC;

@Component
public class OrderOutboxDataAccessMapper {
    public OrderOutboxEntity orderOutboxMessageToOrderOutboxEntity(OrderOutboxMessage orderOutboxMessage) {
        return OrderOutboxEntity.builder()
                .id(orderOutboxMessage.getId())
                .sagaId(orderOutboxMessage.getSagaId())
                .createdAt(Date.from(orderOutboxMessage.getCreatedAt().toInstant()))
                .type(orderOutboxMessage.getType())
                .payload(orderOutboxMessage.getPayload())
                .outboxStatus(orderOutboxMessage.getOutboxStatus().name())
                .paymentStatus(orderOutboxMessage.getPaymentStatus().name())
                .version(orderOutboxMessage.getVersion())
                .build();
    }

    public OrderOutboxMessage orderOutboxEntityToOrderOutboxMessage(OrderOutboxEntity paymentOutboxEntity) {
        return OrderOutboxMessage.builder()
                .id(paymentOutboxEntity.getId())
                .sagaId(paymentOutboxEntity.getSagaId())
                .createdAt(ZonedDateTime.ofInstant(paymentOutboxEntity.getCreatedAt().toInstant(), ZoneId.of(UTC)))
                .type(paymentOutboxEntity.getType())
                .payload(paymentOutboxEntity.getPayload())
                .outboxStatus(OutboxStatus.valueOf(paymentOutboxEntity.getOutboxStatus()))
                .paymentStatus(PaymentStatus.valueOf(paymentOutboxEntity.getPaymentStatus()))
                .version(paymentOutboxEntity.getVersion())
                .build();
    }
}

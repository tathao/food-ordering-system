package com.food.ordering.system.order.db.entity;

import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.saga.SagaStatus;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant_approval_outbox")
@Entity
@EqualsAndHashCode(of = "id")
public class ApprovalOutboxEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "saga_id")
    private UUID sagaId;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "processed_at")
    private ZonedDateTime processedAt;
    @Column(name = "type")
    private String type;
    @Column(name = "payload")
    private String payload;
    @Enumerated(EnumType.STRING)
    @Column(name = "saga_status")
    private SagaStatus sagaStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "outbox_status")
    private OutboxStatus outboxStatus;
    @Version
    private int version;
}

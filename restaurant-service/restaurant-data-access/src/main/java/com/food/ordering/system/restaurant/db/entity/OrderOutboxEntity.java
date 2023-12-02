package com.food.ordering.system.restaurant.db.entity;

import com.food.ordering.system.common.domain.valueobject.OrderApprovalStatus;
import com.food.ordering.system.outbox.OutboxStatus;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant_order_outbox")
@Entity
@EqualsAndHashCode(of = "id")
public class OrderOutboxEntity {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "saga_id")
    private UUID sagaId;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "proccessed_at")
    private ZonedDateTime processedAt;
    @Column(name = "type")
    private String type;
    @Column(name = "payload")
    private String payload;
    @Column(name = "outbox_status")
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;
    @Column(name = "approval_status")
    @Enumerated(EnumType.STRING)
    private OrderApprovalStatus orderApprovalStatus;
    @Column(name = "version")
    private int version;
}

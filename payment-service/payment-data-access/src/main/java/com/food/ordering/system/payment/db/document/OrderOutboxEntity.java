package com.food.ordering.system.payment.db.document;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(of = "id")
@Document(collection = "order_outbox")
public class OrderOutboxEntity {
    @Id
    private UUID id;
    @Field(name = "saga_id")
    private UUID sagaId;
    @CreatedDate
    @Field(name = "created_at")
    private Date createdAt;
    @CreatedDate
    @Field(name = "processed_at")
    private Date processedAt;
    @Field(name = "type", targetType = FieldType.STRING)
    private String type;
    @Field(name = "payload", targetType = FieldType.STRING)
    private String payload;
    @Field(name = "outbox_status", targetType = FieldType.STRING)
    private String outboxStatus;
    @Field(name = "payment_status", targetType = FieldType.STRING)
    private String paymentStatus;
    @Version
    @Field(name = "version")
    private int version;
}

package com.food.ordering.system.payment.db.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Document(collection = "payments")
public class PaymentEntity {

    @Id
    private UUID id;
    @Field(name = "order_id")
    private UUID orderId;
    @Field(name = "customer_id")
    private UUID customerId;
    @Field(name = "amount", targetType = FieldType.DECIMAL128)
    private BigDecimal amount;
    @Field(name = "payment_status", targetType = FieldType.STRING)
    private String paymentStatus;
    @CreatedDate
    private Date createdAt;
}

package com.food.ordering.system.payment.db.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Document(collection = "credit_histories")
public class CreditHistoryEntity {
    @Id
    @Field(name = "id")
    private UUID id;
    @Field(name = "customer_id")
    private UUID customerId;
    @Field(name = "amount", targetType = FieldType.DECIMAL128)
    private BigDecimal amount;
    @Field(name = "transaction_type")
    private String transactionType;
}

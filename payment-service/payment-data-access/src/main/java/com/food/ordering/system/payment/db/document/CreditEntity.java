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
@Document(collection = "credit")
public class CreditEntity {

    @Id
    private UUID id;
    @Field(name = "customer_id")
    private UUID customerId;
    @Field(name = "first_name")
    private String firstName;
    @Field(name = "user_name")
    private String userName;
    @Field(name = "last_name")
    private String lastName;
    @Field(name = "active")
    private Boolean active = true;
    @Field(name = "total_credit_amount", targetType = FieldType.DECIMAL128)
    private BigDecimal totalCreditAmount;
}

package com.food.ordering.system.payment.db.mapper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.OrderId;
import com.food.ordering.system.common.domain.valueobject.PaymentStatus;
import com.food.ordering.system.payment.db.document.CreditEntity;
import com.food.ordering.system.payment.db.document.CreditHistoryEntity;
import com.food.ordering.system.payment.db.document.PaymentEntity;
import com.food.ordering.system.payment.domain.entity.Credit;
import com.food.ordering.system.payment.domain.entity.CreditHistory;
import com.food.ordering.system.payment.domain.entity.Payment;
import com.food.ordering.system.payment.domain.valueobject.CreditHistoryId;
import com.food.ordering.system.payment.domain.valueobject.CreditId;
import com.food.ordering.system.payment.domain.valueobject.PaymentId;
import com.food.ordering.system.payment.domain.valueobject.TransactionType;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.food.ordering.system.common.domain.constant.DomainConstant.UTC;

@Component
public class PaymentDataAccessMapper {

    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId().getValue())
                .orderId(payment.getOrderId().getValue())
                .customerId(payment.getCustomerId().getValue())
                .paymentStatus(payment.getPaymentStatus().name())
                .amount(payment.getAmount().getAmount())
                .createdAt(Date.from(payment.getCreatedAt().toInstant()))
                .build();
    }

    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        return Payment.builder()
                .paymentId(new PaymentId(paymentEntity.getId()))
                .orderId(new OrderId(paymentEntity.getOrderId()))
                .customerId(new CustomerId(paymentEntity.getCustomerId()))
                .amount(new Money(paymentEntity.getAmount()))
                .paymentStatus(PaymentStatus.valueOf(paymentEntity.getPaymentStatus()))
                .createdAt(ZonedDateTime.ofInstant(paymentEntity.getCreatedAt().toInstant(), ZoneId.of(UTC)))
                .build();
    }

    public Optional<Credit> creditEntityToOptionalCredit(CreditEntity credit) {
        return Optional.of(Credit.builder()
                        .creditEntryId(new CreditId(credit.getId()))
                        .customerId(new CustomerId(credit.getCustomerId()))
                        .totalCreditAmount(new Money(credit.getTotalCreditAmount()))
                .build());
    }


    public CreditEntity creditToCreditEntity(Credit credit) {
        return CreditEntity.builder()
                .id(credit.getId().getValue())
                .customerId(credit.getCustomerId().getValue())
                .firstName(credit.getFirstName())
                .lastName(credit.getLastName())
                .active(credit.getAcrive())
                .totalCreditAmount(credit.getTotalCreditAmount().getAmount())
                .build();
    }

    public CreditHistoryEntity creditHistoryToCreditHistoryEntity(CreditHistory creditHistory) {
        return CreditHistoryEntity.builder()
                .id(creditHistory.getId().getValue())
                .transactionType(creditHistory.getTransactionType().name())
                .amount(creditHistory.getAmount().getAmount())
                .customerId(creditHistory.getCustomerId().getValue())
                .build();
    }

    public CreditHistory creditHistoryEntityToCreditHistory(CreditHistoryEntity creditHistoryEntity) {
        return CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(creditHistoryEntity.getId()))
                .amount(new Money(creditHistoryEntity.getAmount()))
                .customerId(new CustomerId(creditHistoryEntity.getCustomerId()))
                .transactionType(TransactionType.valueOf(creditHistoryEntity.getTransactionType()))
                .build();
    }

    public List<CreditHistory> creditHistoryEntitiesToCreditHistories(List<CreditHistoryEntity> creditHistoryEntities) {
        return creditHistoryEntities.stream().map(
                creditHistoryEntity -> CreditHistory.builder()
                        .creditHistoryId(new CreditHistoryId(creditHistoryEntity.getId()))
                        .amount(new Money(creditHistoryEntity.getAmount()))
                        .transactionType(TransactionType.valueOf(creditHistoryEntity.getTransactionType()))
                        .customerId(new CustomerId(creditHistoryEntity.getCustomerId()))
                        .build()
        ).collect(Collectors.toList());
    }
}

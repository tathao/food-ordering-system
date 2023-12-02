package com.food.ordering.system.payment.domain.impl;

import com.food.ordering.system.common.domain.valueobject.PaymentStatus;
import com.food.ordering.system.payment.domain.PaymentDomainService;
import com.food.ordering.system.payment.domain.entity.Credit;
import com.food.ordering.system.payment.domain.entity.CreditHistory;
import com.food.ordering.system.payment.domain.entity.Payment;
import com.food.ordering.system.payment.domain.event.PaymentCancelledEvent;
import com.food.ordering.system.payment.domain.event.PaymentCompletedEvent;
import com.food.ordering.system.payment.domain.event.PaymentEvent;
import com.food.ordering.system.payment.domain.event.PaymentFailedEvent;
import com.food.ordering.system.payment.domain.valueobject.CreditHistoryId;
import com.food.ordering.system.payment.domain.valueobject.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static com.food.ordering.system.common.domain.constant.DomainConstant.UTC;

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService {
    @Override
    public PaymentEvent validateAndInitiatePayment(Payment payment, Credit credit,
                                                   List<CreditHistory> creditHistories,
                                                   List<String> failureMessages) {
        payment.validatePayment(failureMessages);
        payment.initializePayment();
        validateCreditEntry(payment, credit, failureMessages);
        subtractCreditEntry(payment, credit);
        updateCreditHistory(payment, creditHistories, TransactionType.DEBIT);

        if (failureMessages.isEmpty()) {
            log.info("Payment is initiated for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.COMPLETED);
            return new PaymentCompletedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)));

        }
        log.info("Payment initiation is failed for order id: {}", payment.getOrderId().getValue());
        payment.updateStatus(PaymentStatus.FAILED);
        return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), failureMessages);
    }

    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment, Credit credit,
                                         List<CreditHistory> creditHistories,
                                         List<String> failureMessages) {
        payment.validatePayment(failureMessages);
        addCredit(payment, credit);
        updateCreditHistory(payment, creditHistories, TransactionType.CREDIT);
        if (failureMessages.isEmpty()) {
            log.info("Payment is cancelled for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.CANCELLED);
            return new PaymentCancelledEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)));
        }
        log.info("Payment cancellation is failed for order id: {}", payment.getOrderId().getValue());
        payment.updateStatus(PaymentStatus.FAILED);
        return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), failureMessages);
    }

    private void addCredit(Payment payment, Credit credit) {
        credit.addCreditAmount(payment.getAmount());
    }


    private void updateCreditHistory(Payment payment, List<CreditHistory> creditHistories, TransactionType transactionType) {
        CreditHistory creditHistory = CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(UUID.randomUUID()))
                .customerId(payment.getCustomerId())
                .amount(payment.getAmount())
                .transactionType(transactionType)
                .build();
        creditHistories.add(creditHistory);
    }

    private void subtractCreditEntry(Payment payment, Credit credit) {
        credit.subtractCreditAmount(payment.getAmount());
    }

    private void validateCreditEntry(Payment payment, Credit credit, List<String> failureMessages) {
        if (payment.getAmount().isGreaterThan(credit.getTotalCreditAmount())) {
            log.error("Customer with id: {} doesn't have enough credit for payment!",
                    payment.getCustomerId().getValue());
            failureMessages.add("Customer with id: " + payment.getCustomerId().getValue()
                    + " doesn't have enough credit for payment!");
        }
    }
}

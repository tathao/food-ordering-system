package com.food.ordering.system.payment.domain;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.payment.domain.entity.Credit;
import com.food.ordering.system.payment.domain.entity.CreditHistory;
import com.food.ordering.system.payment.domain.entity.Payment;
import com.food.ordering.system.payment.domain.event.PaymentCompletedEvent;
import com.food.ordering.system.payment.domain.event.PaymentEvent;
import com.food.ordering.system.payment.domain.event.PaymentFailedEvent;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment, Credit credit,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages,
                                            DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher,
                                            DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);

}

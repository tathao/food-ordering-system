package com.food.ordering.system.payment.service.helper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.payment.domain.PaymentDomainService;
import com.food.ordering.system.payment.domain.entity.Credit;
import com.food.ordering.system.payment.domain.entity.CreditHistory;
import com.food.ordering.system.payment.domain.entity.Payment;
import com.food.ordering.system.payment.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.dto.PaymentRequest;
import com.food.ordering.system.payment.service.exception.PaymentDomainServiceException;
import com.food.ordering.system.payment.service.mapper.PaymentDataMapper;
import com.food.ordering.system.payment.service.ports.output.message.publisher.PaymentCompleteMessagePublisher;
import com.food.ordering.system.payment.service.ports.output.message.publisher.PaymentFailedMessagePublisher;
import com.food.ordering.system.payment.service.ports.output.repository.CreditHistoryRepository;
import com.food.ordering.system.payment.service.ports.output.repository.CreditRepository;
import com.food.ordering.system.payment.service.ports.output.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRequestHelper {

    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper paymentDataMapper;
    private final CreditRepository creditRepository;
    private final CreditHistoryRepository creditHistoryRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentCompleteMessagePublisher paymentCompletedEventDomainEventPublisher;
    private final PaymentFailedMessagePublisher paymentFailedEventDomainEventPublisher;

    @Transactional
    public PaymentEvent persistCompletePayment(PaymentRequest paymentRequest) {
        log.info("Received payment complete event for order id: {}", paymentRequest.getOrderId());
        var payment = paymentDataMapper.paymentRequestToPayment(paymentRequest);
        var credit = getCredit(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistories(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        var paymentEvent =
                paymentDomainService.validateAndInitiatePayment(payment, credit, creditHistories, failureMessages, paymentCompletedEventDomainEventPublisher, paymentFailedEventDomainEventPublisher);
        persistDoObjects(payment, failureMessages, credit, creditHistories);
        return paymentEvent;
    }

    private void persistDoObjects(Payment payment, List<String> failureMessages, Credit credit, List<CreditHistory> creditHistories) {
        paymentRepository.save(payment);
        if (failureMessages.isEmpty()) {
            creditRepository.save(credit);
            creditHistoryRepository.save(creditHistories.get(creditHistories.size() - 1));
        }
    }

    private List<CreditHistory> getCreditHistories(CustomerId customerId) {
        return creditHistoryRepository.findAllByCustomerId(customerId)
                .orElse(new ArrayList<>());
    }

    private Credit getCredit(CustomerId customerId) {
        return creditRepository.findByCustomerId(customerId)
                .orElseThrow(()->{
                    log.error("Could not find credit entry for customer: {}", customerId.getValue());
                    return new PaymentDomainServiceException("Could not find credit entry for customer " + customerId.getValue());
                });
    }
}

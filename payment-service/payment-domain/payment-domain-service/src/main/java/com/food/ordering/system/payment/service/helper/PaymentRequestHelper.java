package com.food.ordering.system.payment.service.helper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.PaymentStatus;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.payment.domain.PaymentDomainService;
import com.food.ordering.system.payment.domain.entity.Credit;
import com.food.ordering.system.payment.domain.entity.CreditHistory;
import com.food.ordering.system.payment.domain.entity.Payment;
import com.food.ordering.system.payment.domain.event.PaymentEvent;
import com.food.ordering.system.payment.domain.exception.PaymentNotFoundException;
import com.food.ordering.system.payment.service.dto.PaymentRequest;
import com.food.ordering.system.payment.service.exception.PaymentDomainServiceException;
import com.food.ordering.system.payment.service.mapper.PaymentDataMapper;
import com.food.ordering.system.payment.service.outbox.model.OrderOutboxMessage;
import com.food.ordering.system.payment.service.outbox.scheduler.OrderOutboxHelper;
import com.food.ordering.system.payment.service.ports.output.message.publisher.PaymentResponseMessagePublisher;
import com.food.ordering.system.payment.service.ports.output.repository.CreditHistoryRepository;
import com.food.ordering.system.payment.service.ports.output.repository.CreditRepository;
import com.food.ordering.system.payment.service.ports.output.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRequestHelper {

    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper paymentDataMapper;
    private final CreditRepository creditRepository;
    private final CreditHistoryRepository creditHistoryRepository;
    private final PaymentRepository paymentRepository;
    private final OrderOutboxHelper orderOutboxHelper;
    private final PaymentResponseMessagePublisher paymentResponseMessagePublisher;

    @Transactional
    public void persistCompletePayment(PaymentRequest paymentRequest) {
        if (publishIfOutboxMessageProcessedForPayment(paymentRequest, PaymentStatus.COMPLETED)) {
            log.info("An outbox message with saga id: {} is already saved to database!",
                    paymentRequest.getSagaId());
            return;
        }
        log.info("Received payment complete event for order id: {}", paymentRequest.getOrderId());
        var payment = paymentDataMapper.paymentRequestToPayment(paymentRequest);
        var credit = getCredit(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistories(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        var paymentEvent =paymentDomainService.validateAndInitiatePayment(payment, credit, creditHistories, failureMessages);
        persistDoObjects(payment, failureMessages, credit, creditHistories);

        orderOutboxHelper.saveOrderOutboxMessage(paymentDataMapper.paymentEventToOrderEventPayload(paymentEvent),
                paymentEvent.getPayment().getPaymentStatus(),
                OutboxStatus.STARTED,
                UUID.fromString(paymentRequest.getSagaId()));
    }

    public void persistCancelPayment(PaymentRequest paymentRequest) {
        if (publishIfOutboxMessageProcessedForPayment(paymentRequest, PaymentStatus.CANCELLED)) {
            log.info("An outbox message with saga id: {} is already saved to database!",
                    paymentRequest.getSagaId());
            return;
        }
        log.info("Received payment rollback event for order id: {}",
                paymentRequest.getOrderId());
        Optional<Payment> paymentResponse = paymentRepository
                .findByOrderId(UUID.fromString(paymentRequest.getOrderId()));
        if (paymentResponse.isEmpty()) {
            log.error("Payment with order id: {} could not be found!",
                    paymentRequest.getOrderId());
            throw new PaymentNotFoundException("Payment with order id: "
            + paymentRequest.getOrderId() + " could not be found!");
        }
        var payment = paymentResponse.get();
        var credit = getCredit(payment.getCustomerId());
        List<CreditHistory> creditHistories =
                getCreditHistories(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();

        var paymentEvent =paymentDomainService.validateAndCancelPayment(payment, credit, creditHistories,
                        failureMessages);
        persistDoObjects(payment, failureMessages, credit, creditHistories);

        orderOutboxHelper.saveOrderOutboxMessage(paymentDataMapper.paymentEventToOrderEventPayload(paymentEvent),
                paymentEvent.getPayment().getPaymentStatus(),
                OutboxStatus.STARTED,
                UUID.fromString(paymentRequest.getSagaId()));
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
                }
                );
    }

    private boolean publishIfOutboxMessageProcessedForPayment(PaymentRequest paymentRequest,
                                                              PaymentStatus paymentStatus) {
        Optional<OrderOutboxMessage> orderOutboxMessage =
                orderOutboxHelper.getCompletedOrderOutboxMessageBySagaIdAndPaymentStatus(
                        UUID.fromString(paymentRequest.getSagaId()),
                        paymentStatus);
        if (orderOutboxMessage.isPresent()) {
            paymentResponseMessagePublisher.publish(orderOutboxMessage.get(), orderOutboxHelper::updateOutboxMessage);
            return true;
        }
        return false;
    }
}

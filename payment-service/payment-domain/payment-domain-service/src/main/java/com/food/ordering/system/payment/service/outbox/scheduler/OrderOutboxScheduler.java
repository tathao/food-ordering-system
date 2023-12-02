package com.food.ordering.system.payment.service.outbox.scheduler;

import com.food.ordering.system.outbox.OutboxScheduler;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.payment.service.outbox.model.OrderOutboxMessage;
import com.food.ordering.system.payment.service.ports.output.message.publisher.PaymentResponseMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.food.ordering.system.common.domain.constant.DomainConstant.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderOutboxScheduler implements OutboxScheduler {

    private final OrderOutboxHelper orderOutboxHelper;
    private final PaymentResponseMessagePublisher paymentResponseMessagePublisher;
    @Override
    @Scheduled(fixedRateString = "${payment-service.outbox-scheduler-fixed-rate}",
    initialDelayString = "${payment-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        Optional<List<OrderOutboxMessage>> orderOutboxMessagesResponse =
                orderOutboxHelper.getOrderOutboxMessageByOutboxStatus(OutboxStatus.STARTED);
        if (orderOutboxMessagesResponse.isPresent() && !orderOutboxMessagesResponse.get().isEmpty()) {
            List<OrderOutboxMessage> orderOutboxMessages = orderOutboxMessagesResponse.get();
            log.info("Received {} OrderOutboxMessage with ids {}, sending to kafka!", orderOutboxMessages.size(),
                    orderOutboxMessages.stream()
                            .map(orderOutboxMessage ->
                                    orderOutboxMessage.getId().toString()).collect(Collectors.joining(FAILURE_MESSAGE_DELIMITER)));
            orderOutboxMessages.forEach(
                    orderOutboxMessage ->
                            paymentResponseMessagePublisher.publish(orderOutboxMessage, orderOutboxHelper::updateOutboxMessage)
            );
            log.info("{} OrderOutboxMessage sent to message bus!", orderOutboxMessages.size());
        }
    }
}

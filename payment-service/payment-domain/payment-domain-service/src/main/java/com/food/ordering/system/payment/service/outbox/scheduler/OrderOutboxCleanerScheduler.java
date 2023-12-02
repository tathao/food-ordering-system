package com.food.ordering.system.payment.service.outbox.scheduler;

import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import com.food.ordering.system.outbox.OutboxScheduler;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.payment.service.outbox.model.OrderOutboxMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderOutboxCleanerScheduler implements OutboxScheduler {
    private final OrderOutboxHelper orderOutboxHelper;
    @Override
    @Scheduled(cron = "@midnight")
    public void processOutboxMessage() {
        Optional<List<OrderOutboxMessage>> orderOutboxMessagesResponse =
                orderOutboxHelper.getOrderOutboxMessageByOutboxStatus(
                        OutboxStatus.COMPLETED
                );
        if (orderOutboxMessagesResponse.isPresent() &&
                !orderOutboxMessagesResponse.get().isEmpty()) {
            List<OrderOutboxMessage> orderOutboxMessages =
                    orderOutboxMessagesResponse.get();
            log.info("Received {} OrderOutboxMessage for clean up!",
                    orderOutboxMessages.size());
            orderOutboxHelper.deleteOrderOutboxMessageByOutboxStatus(
                    OutboxStatus.COMPLETED
            );
            log.info("Deleted {} OrderOutboxMessage!",
                    orderOutboxMessages.size());
        }
    }
}

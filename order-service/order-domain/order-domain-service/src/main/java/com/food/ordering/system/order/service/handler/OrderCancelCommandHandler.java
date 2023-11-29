package com.food.ordering.system.order.service.handler;

import com.food.ordering.system.order.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.helper.OrderCancelledHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCancelCommandHandler {

    private final OrderCancelledHelper orderCancelledHelper;

    public void cancelOrderPayment(PaymentResponse paymentResponse) {
        OrderCancelledEvent orderCancelledEvent = orderCancelledHelper.persistOrderCancelled(paymentResponse);
        log.info("Order is cancelled with id: {}", orderCancelledEvent.getOrder().getId().getValue());
        fireEvent(orderCancelledEvent);
    }

    private void fireEvent(OrderCancelledEvent orderCancelledEvent) {
        log.info("Publishing order cancelled event with order id: {}",
                orderCancelledEvent.getOrder().getId().getValue());
        orderCancelledEvent.fire();
    }
}

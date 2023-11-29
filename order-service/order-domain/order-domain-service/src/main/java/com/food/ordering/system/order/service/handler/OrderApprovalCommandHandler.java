package com.food.ordering.system.order.service.handler;

import com.food.ordering.system.order.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.service.helper.OrderApprovalHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderApprovalCommandHandler {

    private final OrderApprovalHelper orderApprovalHelper;

    public void orderApprovalToRestaurant(PaymentResponse paymentResponse) {
        OrderPaidEvent orderPaidEvent = orderApprovalHelper.persistOrderToRestaurant(paymentResponse);
        log.info("Order is paid with id: {}", orderPaidEvent.getOrder().getId().getValue());
        fireEvent(orderPaidEvent);
    }

    private void fireEvent(OrderPaidEvent orderPaidEvent) {
        log.info("Publishing event with order id: {} and restaurant id: {}",
                orderPaidEvent.getOrder().getId().getValue(),
                orderPaidEvent.getOrder().getRestaurantId().getValue());
        orderPaidEvent.fire();
    }

    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {
        orderApprovalHelper.persistOrderApproved(restaurantApprovalResponse);
    }
}

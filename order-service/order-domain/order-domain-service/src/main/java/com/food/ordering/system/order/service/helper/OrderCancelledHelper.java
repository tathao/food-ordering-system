package com.food.ordering.system.order.service.helper;

import com.food.ordering.system.order.domain.OrderDomainService;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.service.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.ports.output.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCancelledHelper {

    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;
    private final OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher;
    @Transactional
    public OrderCancelledEvent persistOrderCancelled(PaymentResponse paymentResponse) {
        Order order = orderRepository.findById(UUID.fromString(paymentResponse.getOrderId()))
                .orElseThrow(()-> new OrderNotFoundException("Could not find order with id: "
                        + paymentResponse.getOrderId()));
        List<String> failureMessages = new ArrayList<>();
        OrderCancelledEvent orderCancelledEvent = orderDomainService.cancelOrderPayment(order, failureMessages,
                orderCancelledPaymentRequestMessagePublisher);
        orderRepository.saveOrder(order);
        log.info("Order is persisted with id: {}", order.getId().getValue());
        return orderCancelledEvent;
    }
}

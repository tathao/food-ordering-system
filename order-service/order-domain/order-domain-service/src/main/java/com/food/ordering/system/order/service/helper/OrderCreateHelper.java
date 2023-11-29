package com.food.ordering.system.order.service.helper;

import com.food.ordering.system.order.domain.OrderDomainService;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.entity.Product;
import com.food.ordering.system.order.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.dto.create.OrderItem;
import com.food.ordering.system.order.service.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.ports.output.message.publisher.payment.OrderCreatePaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.ports.output.repository.OrderItemRepository;
import com.food.ordering.system.order.service.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.ports.output.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateHelper {

    private final OrderDataMapper orderDataMapper;
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final OrderCreatePaymentRequestMessagePublisher orderCreatedEventDomainEventPublisher;


    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderRequest createOrderRequest) {
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderRequest);

        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order,
                orderCreatedEventDomainEventPublisher);
        orderRepository.saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

}

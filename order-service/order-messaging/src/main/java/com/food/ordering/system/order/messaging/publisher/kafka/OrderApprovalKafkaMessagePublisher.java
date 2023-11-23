package com.food.ordering.system.order.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.food.ordering.system.kafka.producer.KafkaProducer;
import com.food.ordering.system.kafka.producer.helper.KafkaMessageHelper;
import com.food.ordering.system.order.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.messaging.mapper.OrderMessagingDataMapper;
import com.food.ordering.system.order.service.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.ports.output.message.publisher.payment.OrderApprovalRestaurantMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderApprovalKafkaMessagePublisher implements OrderApprovalRestaurantMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;
    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();
        String restaurantId = domainEvent.getOrder().getRestaurantId().getValue().toString();
        log.info("Received OrderPaidEvent for order id: {} and restaurant id: {}", orderId, restaurantId);
        try {
            RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel =
                    orderMessagingDataMapper.restaurantApprovalRequestAvroModel(domainEvent);
            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    orderId,
                    restaurantApprovalRequestAvroModel,
                    orderKafkaMessageHelper
                            .getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(),
                                    restaurantApprovalRequestAvroModel,
                                    orderId,
                                    "RestaurantApprovalRequestAvroModel"));
        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel message " +
                    "to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }
}

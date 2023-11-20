package com.food.ordering.system.order.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.producer.KafkaProducer;
import com.food.ordering.system.kafka.producer.helper.KafkaMessageHelper;
import com.food.ordering.system.order.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.messaging.mapper.OrderMessagingDataMapper;
import com.food.ordering.system.order.service.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.ports.output.message.publisher.payment.OrderCreatePaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderKafkaMessagePublisher implements OrderCreatePaymentRequestMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;


    @Override
    public void publish(OrderCreatedEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();
        log.info("Received OrderCreatedEvent for order id: {}", orderId);
        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper
                    .orderCreatedEventToPaymentRequestAvroModel(domainEvent);
            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel,
                    orderKafkaMessageHelper
                            .getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(),
                                    paymentRequestAvroModel,
                                    orderId,
                                    "PaymentRequestAvroModel"));
        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel message " +
                    "to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }
}

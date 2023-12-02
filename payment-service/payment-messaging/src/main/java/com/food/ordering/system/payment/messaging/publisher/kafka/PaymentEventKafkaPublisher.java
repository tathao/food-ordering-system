package com.food.ordering.system.payment.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel;
import com.food.ordering.system.kafka.producer.KafkaProducer;
import com.food.ordering.system.kafka.producer.helper.KafkaMessageHelper;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.payment.messaging.mapper.PaymentMessagingDataMapper;
import com.food.ordering.system.payment.service.config.PaymentServiceConfigData;
import com.food.ordering.system.payment.service.outbox.model.OrderEventPayload;
import com.food.ordering.system.payment.service.outbox.model.OrderOutboxMessage;
import com.food.ordering.system.payment.service.ports.output.message.publisher.PaymentResponseMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventKafkaPublisher implements PaymentResponseMessagePublisher {

    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    private final PaymentServiceConfigData paymentServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    @Override
    public void publish(OrderOutboxMessage orderOutboxMessage,
                        BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback) {
        OrderEventPayload orderEventPayload = kafkaMessageHelper.getOrderEventPayload(
                orderOutboxMessage.getPayload(), OrderEventPayload.class
        );
        String sagaId = orderOutboxMessage.getSagaId().toString();
        log.info("Received OrderOutboxMessage for order id: {} and saga id: {}",
                orderEventPayload.getOrderId(),sagaId);
        try {
            PaymentResponseAvroModel paymentResponseAvroModel =
                    paymentMessagingDataMapper
                            .orderEventPayloadToPaymentResponseAvroModel(
                                    sagaId, orderEventPayload
                            );
            kafkaProducer.send(paymentServiceConfigData.getPaymentResponseTopicName(),
                    sagaId,
                    paymentResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallback(
                            paymentServiceConfigData.getPaymentResponseTopicName(),
                            paymentResponseAvroModel,
                            orderOutboxMessage,
                            outboxCallback,
                            orderEventPayload.getOrderId(),
                            "PaymentResponseAvroModel"
                    ));
            log.info("PaymentResponseAvroModel sent to kafka for order id: {}" +
                    " and saga id: {}",
                    paymentResponseAvroModel.getOrderId(),sagaId);
        } catch (Exception e) {
            log.error("Error while sending PaymentResponseAvroModel message " +
                    "to kafka with order id: {} and saga id: {}, error: {}",
                    orderEventPayload.getOrderId(), sagaId, e.getMessage());
        }
    }
}

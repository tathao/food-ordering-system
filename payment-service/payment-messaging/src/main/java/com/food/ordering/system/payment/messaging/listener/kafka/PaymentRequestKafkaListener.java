package com.food.ordering.system.payment.messaging.listener.kafka;

import com.food.ordering.system.kafka.consumer.KafkaConsumer;
import com.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus;
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.payment.domain.exception.PaymentDomainException;
import com.food.ordering.system.payment.domain.exception.PaymentNotFoundException;
import com.food.ordering.system.payment.messaging.mapper.PaymentMessagingDataMapper;
import com.food.ordering.system.payment.service.PaymentDomainApplicationService;
import com.food.ordering.system.payment.service.exception.PaymentDomainServiceException;
import com.mongodb.MongoWriteException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentRequestKafkaListener implements KafkaConsumer<PaymentRequestAvroModel> {

    private final PaymentDomainApplicationService paymentDomainApplicationService;
    private final PaymentMessagingDataMapper paymentMessagingDataMapper;

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${payment-service.payment-request-topic-name}")
    public void receive(@Payload List<PaymentRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("{} number of payment requests received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());
        messages.forEach(paymentRequestAvroModel -> {
            try {
                if (PaymentOrderStatus.PENDING.equals(paymentRequestAvroModel.getPaymentOrderStatus())) {
                    paymentDomainApplicationService.completePayment(
                            paymentMessagingDataMapper
                                    .paymentRequestAvroModelToPaymentRequest(paymentRequestAvroModel)
                    );
                } else {
                    log.info("Cancelling payment for order id: {}", paymentRequestAvroModel.getOrderId());
                    paymentDomainApplicationService.cancelledPayment(
                            paymentMessagingDataMapper.paymentRequestAvroModelToPaymentRequest(paymentRequestAvroModel)
                    );
                }
            } catch (MongoWriteException e) {
                if (e.getCode()==11000) {
                    log.error("Caught unique constraint exception in PaymentRequestKafkaListener for order id: {}",
                            paymentRequestAvroModel.getOrderId());
                } else {
                    throw new PaymentDomainException("Throwing MongoWriteException in " +
                            "PaymentRequestKafkaListener: " + e.getMessage(), e);
                }
            }catch (PaymentNotFoundException e) {
                // NO-OP for PaymentNotFoundException
                log.error("No payment found for order id: {}", paymentRequestAvroModel.getOrderId());
            }
        });
    }
}

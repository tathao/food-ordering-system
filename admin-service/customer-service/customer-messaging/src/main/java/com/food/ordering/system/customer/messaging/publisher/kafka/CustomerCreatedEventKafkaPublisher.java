package com.food.ordering.system.customer.messaging.publisher.kafka;

import com.food.ordering.system.customer.messaging.mapper.CustomerMessagingDataMapper;
import com.food.ordering.system.customer.service.config.CustomerServiceConfigData;
import com.food.ordering.system.customer.service.ports.output.message.publisher.CustomerMessagePublisher;
import com.food.ordering.system.kafka.order.avro.model.CustomerAvroModel;
import com.food.ordering.system.kafka.producer.KafkaProducer;
import com.food.ordering.system.order.domain.event.CustomerEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerCreatedEventKafkaPublisher implements CustomerMessagePublisher {

    private final CustomerMessagingDataMapper customerMessagingDataMapper;
    private final KafkaProducer<String, CustomerAvroModel> kafkaProducer;
    private final CustomerServiceConfigData customerServiceConfigData;

    @Override
    public void publish(CustomerEvent customerEvent) {
        log.info("Received CustomerCreatedEvent for customer id: {}",
                customerEvent.getCustomer().getId().getValue());
        try {
            CustomerAvroModel customerAvroModel = customerMessagingDataMapper
                    .paymentResponseAvroModelToPaymentResponse(customerEvent);

            kafkaProducer.send(customerServiceConfigData.getCustomerTopicName(), customerAvroModel.getId(),
                    customerAvroModel,
                    getCallback(customerServiceConfigData.getCustomerTopicName(), customerAvroModel));

            log.info("CustomerCreatedEvent sent to kafka for customer id: {}",
                    customerAvroModel.getId());
        } catch (Exception e) {
            log.error("Error while sending CustomerCreatedEvent to kafka for customer id: {}," +
                    " error: {}", customerEvent.getCustomer().getId().getValue(), e.getMessage());
        }
    }

    private ListenableFutureCallback<SendResult<String, CustomerAvroModel>>
    getCallback(String topicName, CustomerAvroModel message) {
        return new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error while sending message {} to topic {}", message.toString(), topicName, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, CustomerAvroModel> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            }
        };
    }
}

package com.food.ordering.system.payment.messaging.listener.kafka;

import com.food.ordering.system.kafka.consumer.KafkaConsumer;
import com.food.ordering.system.kafka.order.avro.model.CustomerAvroModel;
import com.food.ordering.system.payment.domain.exception.CustomerDomainException;
import com.food.ordering.system.payment.domain.exception.CustomerNotFoundException;
import com.food.ordering.system.payment.messaging.mapper.CustomerMessagingDataMapper;
import com.food.ordering.system.payment.service.ports.input.message.listener.CustomerRequestMessageListener;
import com.mongodb.MongoWriteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerRequestKafkaListener implements KafkaConsumer<CustomerAvroModel> {
    private final CustomerRequestMessageListener customerRequestMessageListener;
    private final CustomerMessagingDataMapper customerMessagingDataMapper;
    @Override
    @KafkaListener(id = "${kafka-consumer-config.customer-consumer-group-id}",
    topics = "${payment-service.customer-request-topic-name}")
    public void receive(List<CustomerAvroModel> messages,
                        List<String> keys,
                        List<Integer> partitions,
                        List<Long> offsets) {

        log.info("{} number of payment requests received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(customerAvroModel -> {
            try {
                customerRequestMessageListener.createCustomer(
                        customerMessagingDataMapper.customerRequestAvroModelToCustomerRequest(customerAvroModel)
                );
            } catch (MongoWriteException e) {
                if (e.getCode()==11000) {
                    log.error("Caught unique constraint exception in CustomerRequestKafkaListener for customer id: {}",
                            customerAvroModel.getId());
                } else {
                    throw new CustomerDomainException("Throwing MongoWriteException in " +
                            "CustomerRequestKafkaListener: " + e.getMessage(), e);
                }
            }catch (CustomerNotFoundException e) {
                // NO-OP for PaymentNotFoundException
                log.error("No customer found for id: {}", customerAvroModel.getId());
            }
        });
    }
}

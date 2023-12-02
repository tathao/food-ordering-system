package com.food.ordering.system.payment.db.adapter;

import com.food.ordering.system.payment.db.mapper.PaymentDataAccessMapper;
import com.food.ordering.system.payment.db.repository.PaymentMongoRepository;
import com.food.ordering.system.payment.domain.entity.Payment;
import com.food.ordering.system.payment.service.ports.output.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentMongoRepository paymentMongoRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;
    @Override
    public Payment save(Payment payment) {

        return paymentDataAccessMapper.paymentEntityToPayment(paymentMongoRepository
                .save(paymentDataAccessMapper.paymentToPaymentEntity(payment)));
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentMongoRepository.findByOrderId(orderId)
                .map(paymentDataAccessMapper::paymentEntityToPayment);
    }
}

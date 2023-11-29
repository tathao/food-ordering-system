package com.food.ordering.system.payment.db.adapter;

import com.food.ordering.system.payment.db.mapper.PaymentDataAccessMapper;
import com.food.ordering.system.payment.db.repository.PaymentJpaRepository;
import com.food.ordering.system.payment.domain.entity.Payment;
import com.food.ordering.system.payment.service.ports.output.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;
    @Override
    public Payment save(Payment payment) {

        return paymentDataAccessMapper.paymentEntityToPayment(paymentJpaRepository
                .save(paymentDataAccessMapper.paymentToPaymentEntity(payment)));
    }
}

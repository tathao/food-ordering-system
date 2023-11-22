package com.food.ordering.system.payment.db.adapter;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.payment.db.document.CreditEntity;
import com.food.ordering.system.payment.db.mapper.PaymentDataAccessMapper;
import com.food.ordering.system.payment.db.repository.CreditJpaRepository;
import com.food.ordering.system.payment.domain.entity.Credit;
import com.food.ordering.system.payment.service.exception.PaymentDomainServiceException;
import com.food.ordering.system.payment.service.ports.output.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreditRepositoryImpl implements CreditRepository {

    private final CreditJpaRepository creditJpaRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;

    @Override
    public Optional<Credit> findByCustomerId(CustomerId customerId) {
        
        return creditJpaRepository.findByCustomerId(customerId.getValue())
                .map(paymentDataAccessMapper::creditEntityToOptionalCredit)
                .orElseThrow(() -> new PaymentDomainServiceException("Can not find any credit for customer id: " + customerId.getValue()));
    }

    @Override
    public Credit save(Credit credit) {
        CreditEntity creditEntity = creditJpaRepository.save(paymentDataAccessMapper.creditToCreditEntity(credit));
        return paymentDataAccessMapper.creditEntityToOptionalCredit(creditEntity)
                .orElseThrow(()-> new PaymentDomainServiceException("Cannot save the credit to db!"));
    }
}

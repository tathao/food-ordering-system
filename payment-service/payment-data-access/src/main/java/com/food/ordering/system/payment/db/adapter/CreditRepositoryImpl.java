package com.food.ordering.system.payment.db.adapter;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.payment.db.document.CreditEntity;
import com.food.ordering.system.payment.db.mapper.PaymentDataAccessMapper;
import com.food.ordering.system.payment.db.repository.CreditJpaRepository;
import com.food.ordering.system.payment.domain.entity.Credit;
import com.food.ordering.system.payment.domain.exception.PaymentDomainException;
import com.food.ordering.system.payment.domain.exception.PaymentNotFoundException;
import com.food.ordering.system.payment.service.exception.PaymentDomainServiceException;
import com.food.ordering.system.payment.service.ports.output.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
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
        Objects.requireNonNull(credit, "Credit must not be null");
        Objects.requireNonNull(creditJpaRepository, "CreditJpaRepository must not be null");
        Objects.requireNonNull(paymentDataAccessMapper, "PaymentDataAccessMapper must not be null");

        try {
            CreditEntity creditEntity = getCreditEntity(credit);
            creditEntity.setTotalCreditAmount(credit.getTotalCreditAmount().getAmount());

            creditJpaRepository.save(creditEntity);

            CreditEntity savedCreditEntity = creditJpaRepository.findById(creditEntity.getId())
                    .orElseThrow(() -> new PaymentDomainServiceException("CreditEntity not found after saving to the database"));

            if (log.isDebugEnabled()) {
                log.debug("Credit is saved with id: {}", savedCreditEntity.getId());
            }

            return paymentDataAccessMapper.creditEntityToOptionalCredit(savedCreditEntity)
                    .orElseThrow(() -> new PaymentDomainServiceException("Cannot save the credit to db!"));
        } catch (Exception e) {
            log.error("An error occurred while saving the credit. {}", e.getMessage());
            throw new PaymentDomainException("An error occurred while saving the credit", e);
        }
    }

    private CreditEntity getCreditEntity(Credit credit) {
        return creditJpaRepository.findByCustomerId(credit.getCustomerId().getValue())
                .orElseThrow(()-> new PaymentNotFoundException("Cannot find the credit of customer id: "
                + credit.getCustomerId().getValue()));
    }
}

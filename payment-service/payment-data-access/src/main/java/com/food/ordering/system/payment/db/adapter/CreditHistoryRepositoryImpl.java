package com.food.ordering.system.payment.db.adapter;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.payment.db.document.CreditHistoryEntity;
import com.food.ordering.system.payment.db.mapper.PaymentDataAccessMapper;
import com.food.ordering.system.payment.db.repository.CreditHistoryMongoRepository;
import com.food.ordering.system.payment.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.ports.output.repository.CreditHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {
    private final CreditHistoryMongoRepository creditHistoryMongoRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;
    @Override
    public void save(CreditHistory creditHistory) {
        paymentDataAccessMapper.creditHistoryEntityToCreditHistory(creditHistoryMongoRepository.save(paymentDataAccessMapper
                .creditHistoryToCreditHistoryEntity(creditHistory)));
    }

    @Override
    public Optional<List<CreditHistory>> findAllByCustomerId(CustomerId customerId) {
        Optional<List<CreditHistoryEntity>> optionalCreditHistoryEntities = creditHistoryMongoRepository.findAllByCustomerId(customerId.getValue());
        return optionalCreditHistoryEntities.map(paymentDataAccessMapper::creditHistoryEntitiesToCreditHistories);
    }
}

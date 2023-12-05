package com.food.ordering.system.payment.service.helper;

import com.food.ordering.system.payment.domain.entity.Credit;
import com.food.ordering.system.payment.service.dto.CustomerRequest;
import com.food.ordering.system.payment.service.mapper.CustomerDataMapper;
import com.food.ordering.system.payment.service.ports.output.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerRequestHelper {

    private final CustomerDataMapper customerDataMapper;
    private final CreditRepository creditRepository;

    @Transactional
    public void createCustomer(CustomerRequest customerRequest) {
        log.info("Creating new Customer with id: {}", customerRequest.getCustomerId());
        Credit credit = customerDataMapper.customerRequestToCredit(customerRequest);
        persistCredit(credit);
    }

    private void persistCredit(Credit credit) {
        creditRepository.save(credit);
        log.info("Credit saved successful with customer id: {}", credit.getCustomerId().getValue());
    }
}

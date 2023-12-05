package com.food.ordering.system.customer.service.helper;

import com.food.ordering.system.customer.service.dto.create.CreateCustomerRequest;
import com.food.ordering.system.customer.service.mapper.CustomerDataMapper;
import com.food.ordering.system.customer.service.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.domain.CustomerDomainService;
import com.food.ordering.system.order.domain.entity.Customer;
import com.food.ordering.system.order.domain.event.CustomerEvent;
import com.food.ordering.system.order.domain.exception.CustomerDomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerCreateHelper {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;
    private final CustomerDataMapper customerDataMapper;

    @Transactional
    public CustomerEvent createCustomer(CreateCustomerRequest request) {
        Customer customer = customerDataMapper.createCustomerRequestToCustomer(request);
        CustomerEvent customerCreatedEvent = customerDomainService.validateAndInitiateCustomer(customer);
        Customer savedCustomer = customerRepository.createCustomer(customer);
        if (savedCustomer == null) {
            log.error("Could not save customer with id: {}", customer.getId().getValue());
            throw new CustomerDomainException("Could not save customer with id " +
                    customer.getId().getValue());
        }
        log.info("Returning CustomerCreatedEvent for customer id: {}", savedCustomer.getId().getValue());
        return customerCreatedEvent;
    }
}

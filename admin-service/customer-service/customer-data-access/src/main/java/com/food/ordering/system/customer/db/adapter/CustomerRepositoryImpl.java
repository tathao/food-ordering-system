package com.food.ordering.system.customer.db.adapter;

import com.food.ordering.system.customer.db.mapper.CustomerDataAccessMapper;
import com.food.ordering.system.customer.db.repository.CustomerJpaRepository;
import com.food.ordering.system.customer.service.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.domain.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;
    @Override
    public Customer createCustomer(Customer customer) {
        return customerDataAccessMapper.customerEntityToCustomer(
                customerJpaRepository.save(customerDataAccessMapper.customerToCustomerEntity(customer)));
    }
}

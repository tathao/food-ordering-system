package com.food.ordering.system.customer.application.rest.api;

import com.food.ordering.system.customer.service.CustomerCreatedService;
import com.food.ordering.system.customer.service.dto.create.CreateCustomerRequest;
import com.food.ordering.system.customer.service.dto.create.CreateCustomerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/customers", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerCreatedService customerCreatedService;

    @PostMapping
    public ResponseEntity<CreateCustomerResponse> createCustomer(@RequestBody CreateCustomerRequest
                                                                         createCustomerCommand) {
        log.info("Creating customer with username: {}", createCustomerCommand.getUsername());
        CreateCustomerResponse response = customerCreatedService.createCustomer(createCustomerCommand);
        return ResponseEntity.ok(response);
    }
}

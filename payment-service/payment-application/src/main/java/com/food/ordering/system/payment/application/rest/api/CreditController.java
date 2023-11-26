package com.food.ordering.system.payment.application.rest.api;

import com.food.ordering.system.payment.service.PaymentDomainApplicationService;
import com.food.ordering.system.payment.service.dto.CustomerRequest;
import com.food.ordering.system.payment.service.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/payments", produces = "application/vnd.api.v1+json")
public class CreditController {

    private final PaymentDomainApplicationService paymentDomainApplicationService;

    @PostMapping
    public String createCredit(@RequestBody CustomerRequest customerRequest) {
        return paymentDomainApplicationService.creditPayment(customerRequest);
    }
}

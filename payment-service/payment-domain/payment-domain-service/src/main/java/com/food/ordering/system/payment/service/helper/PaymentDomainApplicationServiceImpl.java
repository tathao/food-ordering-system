package com.food.ordering.system.payment.service.helper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.payment.domain.entity.Credit;
import com.food.ordering.system.payment.domain.valueobject.CreditId;
import com.food.ordering.system.payment.service.PaymentDomainApplicationService;
import com.food.ordering.system.payment.service.dto.PaymentRequest;
import com.food.ordering.system.payment.service.ports.input.message.listener.PaymentRequestMessageListener;
import com.food.ordering.system.payment.service.ports.output.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class PaymentDomainApplicationServiceImpl implements PaymentDomainApplicationService {
    private final PaymentRequestMessageListener paymentRequestMessageListener;
    private final CreditRepository creditRepository;
    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        paymentRequestMessageListener.completePayment(paymentRequest);
    }

    @Override
    public String creditPayment(PaymentRequest paymentRequest) {
        Credit credit = Credit.builder()
                .creditEntryId(new CreditId(UUID.randomUUID()))
                .customerId(new CustomerId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41")))
                .totalCreditAmount(new Money(BigDecimal.valueOf(1000.50)))
                .build();
         creditRepository.save(credit);
         return "Success";
    }
}

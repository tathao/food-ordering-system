package com.food.ordering.system.order.service.ports.input.message.listener.payment;

import com.food.ordering.system.order.service.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.dto.message.RestaurantApprovalResponse;

public interface PaymentResponseListener {

    void paymentComplete(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);

}

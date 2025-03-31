package com.shukldi.ecommerce.notification;

import com.shukldi.ecommerce.kafka.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest (
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
){
}

package com.shukldi.ecommerce.kafka;

import com.shukldi.ecommerce.customer.CustomerResponse;
import com.shukldi.ecommerce.order.PaymentMethod;
import com.shukldi.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> product
) {
}

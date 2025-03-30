package com.shukldi.ecommerce.orderline;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,
        @NotNull(message = "Order ID cannot be null")
        Integer orderId,
        @NotNull(message = "Product ID cannot be null")
        Integer productId,
        @Positive(message = "Quantity must be positive")
        double quantity) {
}

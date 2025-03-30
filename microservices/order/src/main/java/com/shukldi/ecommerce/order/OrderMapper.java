package com.shukldi.ecommerce.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setId(orderRequest.id());
        order.setCustomerId(orderRequest.customerId());
        order.setReference(orderRequest.reference());
        order.setTotalAmount(orderRequest.amount());
        order.setPaymentMethod(orderRequest.paymentMethod());
        return order;
    }

    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}

package com.shukldi.ecommerce.order;

import com.shukldi.ecommerce.customer.CustomerClient;
import com.shukldi.ecommerce.customer.CustomerResponse;
import com.shukldi.ecommerce.exception.BusinessException;
import com.shukldi.ecommerce.kafka.OrderConfirmation;
import com.shukldi.ecommerce.kafka.OrderProducer;
import com.shukldi.ecommerce.orderline.OrderLineRequest;
import com.shukldi.ecommerce.orderline.OrderLineService;
import com.shukldi.ecommerce.payment.PaymentClient;
import com.shukldi.ecommerce.payment.PaymentRequest;
import com.shukldi.ecommerce.product.ProductClient;
import com.shukldi.ecommerce.product.PurchaseRequest;
import com.shukldi.ecommerce.product.PurchaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest request) {
        // check the customer
        CustomerResponse customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(()-> new BusinessException("Customer not found with id: " + request.customerId()));

        // purchase the products from product microservice
        List<PurchaseResponse> purchaseResponseList = productClient.purchaseProducts(request.products());

        // persist the order in the database
        Order order = orderRepository.save(
                orderMapper.toOrder(request));

        // persist order lines in the database
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        //start payment process
        paymentClient.requestOrderPayment(
                new PaymentRequest(
                        order.getTotalAmount(),
                        order.getPaymentMethod(),
                        order.getId(),
                        order.getReference(),
                        customer));

        // send the order confirmation to notification microservice (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseResponseList
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new BusinessException("No orders found");
        }
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public OrderResponse findById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found with id: " + id));
        return orderMapper.toOrderResponse(order);
    }
}

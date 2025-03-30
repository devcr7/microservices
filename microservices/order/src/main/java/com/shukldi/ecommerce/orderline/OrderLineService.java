package com.shukldi.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        OrderLine orderLine = orderLineMapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(orderLine).getId();
    }

    public List<OrderLineResponse> getOrderLinesByOrderId(Integer orderId) {
        List<OrderLine> orderLines = orderLineRepository.findAllByOrderId(orderId);
        return orderLines.stream()
                .map(orderLineMapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}

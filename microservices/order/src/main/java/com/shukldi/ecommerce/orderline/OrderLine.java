package com.shukldi.ecommerce.orderline;

import com.shukldi.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "ORDER_LINE")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Integer productId;

    @Column(name = "QUANTITY", nullable = false)
    private double quantity;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;
}
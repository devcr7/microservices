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
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "PRODUCT_ID")
    private Integer productId;

    @Column(name = "QUANTITY")
    private double quantity;

    @Column(name = "PRICE")
    private BigDecimal price;
}
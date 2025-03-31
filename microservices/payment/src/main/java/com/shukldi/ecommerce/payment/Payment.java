package com.shukldi.ecommerce.payment;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "PAYMENT_METHOD")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "ORDER_ID")
    private Integer orderId;
}

package com.shukldi.ecommerce.payment;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE", nullable = false, insertable = false)
    private LocalDateTime lastModifiedDate;

}

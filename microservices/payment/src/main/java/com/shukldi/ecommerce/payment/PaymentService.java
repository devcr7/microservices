package com.shukldi.ecommerce.payment;

import com.shukldi.ecommerce.notification.NotificationProducer;
import com.shukldi.ecommerce.notification.PaymentNotificationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;
    public Integer createPayment(@Valid PaymentRequest paymentRequest) {
        Payment payment = paymentRepository.save(paymentMapper.toPayment(paymentRequest));

        notificationProducer.sendPaymentNotification(
                new PaymentNotificationRequest(
                        paymentRequest.orderReference(),
                        paymentRequest.amount(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.customer().firstName(),
                        paymentRequest.customer().lastName(),
                        paymentRequest.customer().email()
                )
        );

        return payment.getId();
    }
}

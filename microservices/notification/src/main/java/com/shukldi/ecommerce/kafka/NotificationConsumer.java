package com.shukldi.ecommerce.kafka;

import com.shukldi.ecommerce.email.EmailService;
import com.shukldi.ecommerce.kafka.order.OrderConfirmation;
import com.shukldi.ecommerce.kafka.payment.PaymentConfirmation;
import com.shukldi.ecommerce.notification.Notification;
import com.shukldi.ecommerce.notification.NotificationRepository;
import com.shukldi.ecommerce.notification.NotificationType;
import com.shukldi.ecommerce.notification.PaymentNotificationRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentNotificationRequest paymentNotificationRequest) throws MessagingException {
        log.info("Received payment confirmation from payment-topic: {}", paymentNotificationRequest);

        // Save the notification to the database
        Notification notification = Notification.builder()
                .type(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentNotificationRequest)
                .build();

        notificationRepository.save(notification);

        // Send email notification
        String customerName = paymentNotificationRequest.customerFirstName() + " " + paymentNotificationRequest.customerLastName();
        emailService.sendPaymentSuccessEmail(
                paymentNotificationRequest.customerEmail(),
                customerName,
                paymentNotificationRequest.amount(),
                paymentNotificationRequest.orderReference());
    }

    @KafkaListener(topics = "order-topic")
    public void consumePaymentSuccessNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Received order confirmation from order-topic: {}", orderConfirmation);

        // Save the notification to the database
        Notification notification = Notification.builder()
                .type(NotificationType.ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build();

        notificationRepository.save(notification);

        // Send email notification
        String customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        emailService.sendPaymentSuccessEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.amount(),
                orderConfirmation.orderReference());
    }
}

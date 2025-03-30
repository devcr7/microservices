package com.shukldi.ecommerce.email;

import com.shukldi.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(String destinationEmail,
                                        String customerName,
                                        BigDecimal amount,
                                        String orderReference) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setFrom("divyanshu@mail.com");

        final String templateName = EmailTemplate.PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("customerName", customerName);
        templateModel.put("amount", amount);
        templateModel.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(templateModel);
        helper.setSubject(EmailTemplate.PAYMENT_CONFIRMATION.getSubject());

        try {
            String html = templateEngine.process(templateName, context);
            helper.setText(html, true);
            helper.setTo(destinationEmail);
            mailSender.send(message);
            log.info("Payment success email sent to {}", destinationEmail);
        } catch (MessagingException e) {
            log.error("Failed to send payment success email to {}: {}", destinationEmail, e.getMessage());
            throw new MessagingException("Failed to send email", e);
        }
    }

    @Async
    public void sendOrderSuccessEmail(String destinationEmail,
                                        String customerName,
                                        BigDecimal amount,
                                        String orderReference,
                                      List<Product> products) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setFrom("divyanshu@mail.com");

        final String templateName = EmailTemplate.ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("customerName", customerName);
        templateModel.put("totalAmount", amount);
        templateModel.put("orderReference", orderReference);
        templateModel.put("products", products);

        Context context = new Context();
        context.setVariables(templateModel);
        helper.setSubject(EmailTemplate.ORDER_CONFIRMATION.getSubject());

        try {
            String html = templateEngine.process(templateName, context);
            helper.setText(html, true);
            helper.setTo(destinationEmail);
            mailSender.send(message);
            log.info("Order success email sent to {}", destinationEmail);
        } catch (MessagingException e) {
            log.error("Failed to send order success email to {}: {}", destinationEmail, e.getMessage());
            throw new MessagingException("Failed to send email", e);
        }
    }
}

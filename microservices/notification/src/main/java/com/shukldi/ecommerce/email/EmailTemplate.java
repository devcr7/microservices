package com.shukldi.ecommerce.email;

import lombok.Getter;

public enum EmailTemplate {
    PAYMENT_CONFIRMATION("payment_confirmation.html", "Payment successfully processed"),
    ORDER_CONFIRMATION("order_confirmation.html", "Order successfully placed");

    @Getter
    private final String template;
    @Getter
    private final String subject;

    EmailTemplate(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}

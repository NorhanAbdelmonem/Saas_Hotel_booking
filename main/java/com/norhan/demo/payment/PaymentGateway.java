package com.norhan.demo.payment;


import com.norhan.demo.entities.Booking;


import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Booking booking);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
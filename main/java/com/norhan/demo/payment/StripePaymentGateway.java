package com.norhan.demo.payment;

import com.norhan.demo.entities.Booking;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {

    @Value("${WEBSITE_URL}")
    private String websiteUrl;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @Override
    public CheckoutSession createCheckoutSession(Booking booking) {
        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?bookingId=" + booking.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .setPaymentIntentData(createPaymentIntent(booking))
                    .addLineItem(createBookingLineItem(booking));

            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());

        } catch (StripeException ex) {

            throw new PaymentException("Stripe session creation failed");
        }
    }

    private SessionCreateParams.PaymentIntentData createPaymentIntent(Booking booking) {
        return SessionCreateParams.PaymentIntentData.builder()
                .putMetadata("booking_id", String.valueOf(booking.getId()))
                .build();
    }

    private SessionCreateParams.LineItem createBookingLineItem(Booking booking) {
        BigDecimal amountCents = booking.getTotalPrice().movePointRight(2); // $ → cents

        return SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("usd")
                        .setUnitAmountDecimal(amountCents)
                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName("Hotel Booking - Room " + booking.getRoom().getRoomNumber())
                                .build())
                        .build())
                .build();
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        try {
            var payload = request.getPayload();
            var signature = request.getHeaders().get("stripe-signature");
            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);

            return switch (event.getType()) {
                case "payment_intent.succeeded" -> {
                    var pi = extractPaymentIntent(event);
                    var bookingId = extractBookingId(pi);
                    var amount = centsToAmount(pi.getAmountReceived());
                    var currency = pi.getCurrency();
                    yield Optional.of(new PaymentResult(bookingId, PaymentStatus.PAID, amount, currency));
                }
                case "payment_intent.payment_failed" -> {
                    var pi = extractPaymentIntent(event);
                    var bookingId = extractBookingId(pi);
                    var amount = centsToAmount(pi.getAmount()); // مبلغ المحاولة
                    var currency = pi.getCurrency();
                    yield Optional.of(new PaymentResult(bookingId, PaymentStatus.FAILED, amount, currency));
                }
                default -> Optional.empty();
            };
        } catch (SignatureVerificationException e) {
            throw new PaymentException("Invalid Stripe signature");
        }
    }

    /** نحصل على PaymentIntent من الحدث */
    private PaymentIntent extractPaymentIntent(Event event) {
        var stripeObject = event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new PaymentException("Could not deserialize Stripe event."));
        return (PaymentIntent) stripeObject;
    }

    /** نقرأ booking_id من الميتاداتا للـ PaymentIntent */
    private Long extractBookingId(PaymentIntent paymentIntent) {
        var meta = paymentIntent.getMetadata();
        if (meta == null || !meta.containsKey("booking_id")) {
            throw new PaymentException("Missing booking_id metadata in PaymentIntent");
        }
        return Long.valueOf(meta.get("booking_id"));
    }

    /** من سنتات إلى BigDecimal بالدولار؛ تتعامل مع null بأمان */
    private BigDecimal centsToAmount(Long cents) {
        if (cents == null) return null;
        return BigDecimal.valueOf(cents).movePointLeft(2);
    }
}

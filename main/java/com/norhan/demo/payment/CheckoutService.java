package com.norhan.demo.payment;



import com.norhan.demo.booking.BookingNotFoundException;
import com.norhan.demo.repositories.BookingRepository;
import com.norhan.demo.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final BookingRepository bookingRepository;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        var booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException(request.getBookingId()));

        var session = paymentGateway.createCheckoutSession(booking);

        return new CheckoutResponse(booking.getId(), session.getCheckoutUrl());
    }

    public  void handleWebhookEvent(WebhookRequest request) {
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                    var booking = bookingRepository.findById(paymentResult.getBookingId())
                            .orElseThrow(() -> new BookingNotFoundException(paymentResult.getBookingId()));
                    booking.setPaymentStatus(paymentResult.getPaymentStatus());
                    bookingRepository.save(booking);
                });
    }






}
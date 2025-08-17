package com.norhan.demo.controllers;

import com.norhan.demo.booking.BookingNotFoundException;
import com.norhan.demo.common.ErrorDto;
import com.norhan.demo.payment.CheckoutRequest;
import com.norhan.demo.payment.CheckoutResponse;
import com.norhan.demo.payment.PaymentException;
import com.norhan.demo.payment.WebhookRequest;
//import com.stripe.service.CheckoutService;
import com.norhan.demo.payment.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

private final CheckoutService checkoutService;
    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest request) {
        var res = checkoutService.checkout(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
            @RequestHeader Map<String, String> headers,
            @RequestBody String payload
    ) {
        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePaymentException(PaymentException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(ex.getMessage() != null ? ex.getMessage() : "Error creating a checkout session"));
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ErrorDto> handleBookingNotFound(BookingNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage()));
    }

    // حالات عدم صلاحية الحالة (مثلاً: الحجز مش قابل للدفع) أو فاليديشن
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorDto> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }


}
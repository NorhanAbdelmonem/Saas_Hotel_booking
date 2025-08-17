package com.norhan.demo.payment;

import lombok.Data;
import java.util.UUID;

@Data
public class CheckoutResponse {
    private Long bookingId;
    private String checkoutUrl;

    public CheckoutResponse(Long bookingId, String checkoutUrl) {
        this.bookingId = bookingId;
        this.checkoutUrl = checkoutUrl;
    }
}
package com.norhan.demo.payment;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutRequest {
    @NotNull(message = "Booking ID is required.")
    private Long bookingId;
}
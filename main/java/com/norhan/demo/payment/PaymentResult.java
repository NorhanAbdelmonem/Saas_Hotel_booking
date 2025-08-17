package com.norhan.demo.payment;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long bookingId;
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    private String currency;
}
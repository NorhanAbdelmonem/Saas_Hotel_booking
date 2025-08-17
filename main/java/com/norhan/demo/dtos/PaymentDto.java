package com.norhan.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class PaymentDto {
    private Long id;
    private Long bookingId;
    private double amount;
    private String paymentMethod;
    private String status;
    private LocalDateTime paymentDate;
}

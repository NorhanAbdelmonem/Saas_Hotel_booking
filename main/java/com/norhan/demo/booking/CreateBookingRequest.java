package com.norhan.demo.booking;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateBookingRequest {
    private Long roomId;
    private Long userId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
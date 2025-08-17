package com.norhan.demo.booking;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateBookingRequest {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long roomId;
}

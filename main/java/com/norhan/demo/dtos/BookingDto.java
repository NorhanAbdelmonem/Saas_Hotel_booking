package com.norhan.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class BookingDto {
    private Long id;
    private Long roomId;
    private Long userId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double totalPrice;
}

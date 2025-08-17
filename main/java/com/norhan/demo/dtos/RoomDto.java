package com.norhan.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class RoomDto {
    private Long id;
    private String roomNumber;
    private int capacity;
    private BigDecimal pricePerNight;
    private Boolean isAvailable;
    private Long roomTypeId;
    private Long hotelId;
}
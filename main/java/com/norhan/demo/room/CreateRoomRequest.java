package com.norhan.demo.room;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRoomRequest {
    private String roomNumber;
    private int capacity;
    private BigDecimal pricePerNight;
    private Boolean isAvailable;
    private Long roomTypeId;
    private Long hotelId;
}
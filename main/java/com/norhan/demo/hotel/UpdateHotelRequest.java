package com.norhan.demo.hotel;

import lombok.Data;

@Data
public class UpdateHotelRequest {
    private String name;
    private String description;
    private String location;
}
package com.norhan.demo.hotel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateHotelRequest {
    private String name;
    private String description;
    private String location;
    private Long managerId;
}
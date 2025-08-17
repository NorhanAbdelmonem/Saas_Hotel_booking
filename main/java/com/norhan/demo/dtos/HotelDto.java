package com.norhan.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class HotelDto {
    private Long id;
    private String name;
    private String description;
    private String location;
    private Long managerId;

}

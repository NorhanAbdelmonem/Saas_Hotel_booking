package com.norhan.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewDto {
    private Long id;
    private Long hotelId;
    private Long userId;
    private int rating;
    private String comment;
}

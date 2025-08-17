package com.norhan.demo.hotel;

import com.norhan.demo.common.ResourceNotFoundException;

public class HotelNotFoundException extends ResourceNotFoundException {
    public HotelNotFoundException(Long id) {
        super("Hotel with id " + id + " not found");
    }
}

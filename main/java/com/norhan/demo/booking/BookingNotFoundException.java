package com.norhan.demo.booking;

import com.norhan.demo.common.ResourceNotFoundException;

public class BookingNotFoundException extends ResourceNotFoundException {
    public BookingNotFoundException(Long id) {
        super("Booking with id " + id + " not found");
    }
}

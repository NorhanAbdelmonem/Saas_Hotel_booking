package com.norhan.demo.controllers;

import com.norhan.demo.booking.BookingService;
import com.norhan.demo.booking.UpdateBookingRequest;
import com.norhan.demo.dtos.BookingDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/bookings")
@AllArgsConstructor
public class ManagerBookingController {
    private final BookingService bookingService;

    @GetMapping
    public List<BookingDto> getAllBookings(@RequestParam(defaultValue = "createdAt") String sortBy) {
        return bookingService.getAllBookings(sortBy);
    }

    @GetMapping("/{id}")
    public BookingDto getBooking(@PathVariable Long id) {
        return bookingService.getBooking(id);
    }

    @PutMapping("/{id}")
    public BookingDto updateBooking(@PathVariable Long id, @RequestBody UpdateBookingRequest request) {
        return bookingService.updateBooking(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}

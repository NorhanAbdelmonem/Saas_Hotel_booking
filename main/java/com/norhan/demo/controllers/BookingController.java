package com.norhan.demo.controllers;

import com.norhan.demo.booking.BookingService;
import com.norhan.demo.booking.CreateBookingRequest;
import com.norhan.demo.dtos.BookingDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/booking")
public class BookingController {


    private final BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @GetMapping("/me")
    public List<BookingDto> getMyBookings() {
        return bookingService.getMyBookings();
    }


    @PostMapping
    public ResponseEntity<BookingDto> createBooking(
            @Valid @RequestBody CreateBookingRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        BookingDto bookingDto = bookingService.createBooking(request);
        var uri = uriBuilder.path("/bookings/{id}").buildAndExpand(bookingDto.getId()).toUri();
        return ResponseEntity.created(uri).body(bookingDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}

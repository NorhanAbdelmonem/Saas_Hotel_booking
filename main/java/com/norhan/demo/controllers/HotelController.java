package com.norhan.demo.controllers;

import com.norhan.demo.dtos.HotelDto;
import com.norhan.demo.entities.Hotel;
import com.norhan.demo.entities.User;
import com.norhan.demo.hotel.CreateHotelRequest;
import com.norhan.demo.hotel.HotelService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@AllArgsConstructor
public class HotelController {

private HotelService hotelService;
    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllPublicHotels(@RequestParam(defaultValue = "name") String sort) {
        return ResponseEntity.ok(hotelService.getAllPublicHotels(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotel(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotel(id));
    }



}
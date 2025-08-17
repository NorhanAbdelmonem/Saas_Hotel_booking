package com.norhan.demo.controllers;


import com.norhan.demo.dtos.HotelDto;
import com.norhan.demo.hotel.CreateHotelRequest;
import com.norhan.demo.hotel.HotelService;
import com.norhan.demo.hotel.UpdateHotelRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/manager/hotels")
@AllArgsConstructor
public class ManagerHotelController {

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<HotelDto>> myHotels() {
        return ResponseEntity.ok(hotelService.getMyHotels());
    }

    @PostMapping
    public ResponseEntity<HotelDto> create(
            @Valid @RequestBody CreateHotelRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        var dto = hotelService.createMyHotel(request);
        var uri = uriBuilder.path("/manager/hotels/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateHotelRequest request
    ) {
        return ResponseEntity.ok(hotelService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


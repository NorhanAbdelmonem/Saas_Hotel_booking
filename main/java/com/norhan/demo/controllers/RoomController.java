package com.norhan.demo.controllers;

import com.norhan.demo.dtos.HotelDto;
import com.norhan.demo.dtos.RoomDto;
import com.norhan.demo.room.CreateRoomRequest;
import com.norhan.demo.room.RoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDto>> list(@RequestParam(defaultValue = "pricePerNight") String sort) {
        return ResponseEntity.ok(roomService.list(sort));
    }

    @GetMapping("/hotels/{hotelId}/rooms)")
    public ResponseEntity<List<RoomDto>> listByHotel(@RequestParam Long hotelId) {
        return ResponseEntity.ok(roomService.listByHotel(hotelId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

}

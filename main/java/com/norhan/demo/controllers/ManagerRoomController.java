package com.norhan.demo.controllers;


import com.norhan.demo.dtos.RoomDto;
import com.norhan.demo.room.CreateRoomRequest;
import com.norhan.demo.room.RoomService;
import com.norhan.demo.room.RoomUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/manager/rooms")
    @AllArgsConstructor
    public class ManagerRoomController {
        private final RoomService roomService;

        @PostMapping
        public RoomDto createRoom(@RequestBody CreateRoomRequest request) {
            return roomService.create(request);
        }

        @PutMapping("/{id}")
        public RoomDto updateRoom(@PathVariable Long id, @RequestBody RoomUpdateRequest request) {
            return roomService.update(id,request);
        }

        @DeleteMapping("/{id}")
        public void deleteRoom(@PathVariable Long id) {
            roomService.delete(id);
        }
    }


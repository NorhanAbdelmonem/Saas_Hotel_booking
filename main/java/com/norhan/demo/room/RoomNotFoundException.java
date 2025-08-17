package com.norhan.demo.room;

import com.norhan.demo.common.ResourceNotFoundException;

public class RoomNotFoundException extends ResourceNotFoundException {
    public RoomNotFoundException(Long id) {
        super("Room not found: " + id);
    }
}

package com.norhan.demo.room;

import com.norhan.demo.auth.AuthService;
import com.norhan.demo.dtos.RoomDto;
import com.norhan.demo.entities.Hotel;
import com.norhan.demo.entities.Room;
import com.norhan.demo.entities.RoomType;
import com.norhan.demo.hotel.HotelNotFoundException;
import com.norhan.demo.mappers.RoomMapper;
import com.norhan.demo.repositories.HotelRepository;
import com.norhan.demo.repositories.RoomRepository;
import com.norhan.demo.repositories.RoomTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RoomService {
    private RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomMapper roomMapper;
    private final AuthService authService;

    @Transactional
    public RoomDto create(CreateRoomRequest request) {
        var manager = authService.getCurrentUser();
        // هات الفندق اللي اتبعت ID بتاعه وتأكد انه تبع نفس المانجر
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .filter(h -> h.getManager().getId().equals(manager.getId()))
                .orElseThrow(() -> new RuntimeException("Hotel not found for this manager"));
        Room room = roomMapper.toEntity(request);
        room.setHotel(hotel);
        if (request.getRoomTypeId() != null) {
            RoomType rt = roomTypeRepository.findById(request.getRoomTypeId()).orElse(null);
            room.setRoomType(rt);
        }
        room = roomRepository.save(room);
        return roomMapper.toDto(room);
    }


    @Transactional(readOnly = true)
    public List<RoomDto> list(String sortBy) {
        if (!(sortBy.equals("pricePerNight") || sortBy.equals("capacity"))) sortBy = "pricePerNight";
        return roomRepository.findAll(Sort.by(sortBy)).stream().map(roomMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<RoomDto> listByHotel(Long hotelId) {
        return roomRepository.findByHotelId(hotelId).stream().map(roomMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public RoomDto getRoom(Long id) {
        Room r = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
        return roomMapper.toDto(r);
    }

    @Transactional
    public RoomDto update(Long id, RoomUpdateRequest request) {

        var manager = authService.getCurrentUser();
        Room r = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
        if (!r.getHotel().getManager().getId().equals(manager.getId())) {
            throw new RuntimeException("Not authorized to update this room");
        }
        roomMapper.update(request,r);
        r = roomRepository.save(r);
        return roomMapper.toDto(r);
    }

    @Transactional
    public void delete(Long id) {

        var manager = authService.getCurrentUser();

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!room.getHotel().getManager().getId().equals(manager.getId())) {
            throw new RuntimeException("Not authorized to delete this room");
        }
        if (!roomRepository.existsById(id)) throw new RoomNotFoundException(id);
        roomRepository.deleteById(id);
    }
}

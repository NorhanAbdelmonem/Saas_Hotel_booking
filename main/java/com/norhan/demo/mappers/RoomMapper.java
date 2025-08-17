package com.norhan.demo.mappers;

import com.norhan.demo.dtos.RoomDto;
import com.norhan.demo.entities.Room;
import com.norhan.demo.room.CreateRoomRequest;
import com.norhan.demo.room.RoomUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "roomType.id", target = "roomTypeId")
    RoomDto toDto(Room room);

    Room toEntity(CreateRoomRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "roomType", ignore = true)
    void update(RoomUpdateRequest request, @MappingTarget Room room);
}

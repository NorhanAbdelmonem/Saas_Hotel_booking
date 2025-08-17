package com.norhan.demo.mappers;

import com.norhan.demo.booking.CreateBookingRequest;
import com.norhan.demo.booking.UpdateBookingRequest;
import com.norhan.demo.dtos.BookingDto;
import com.norhan.demo.entities.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "user.id", target = "userId")
    BookingDto toDto(Booking booking);

    Booking toEntity(CreateBookingRequest request);

    void update(@MappingTarget Booking booking, UpdateBookingRequest request);
}

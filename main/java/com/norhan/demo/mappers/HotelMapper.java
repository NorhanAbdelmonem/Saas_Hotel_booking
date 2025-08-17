package com.norhan.demo.mappers;

import com.norhan.demo.dtos.HotelDto;
import com.norhan.demo.entities.Hotel;
import com.norhan.demo.hotel.CreateHotelRequest;
import com.norhan.demo.hotel.UpdateHotelRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    @Mapping(source = "manager.id", target = "managerId")
    HotelDto toDto(Hotel hotel);

    Hotel toEntity(CreateHotelRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void update(@MappingTarget Hotel hotel, UpdateHotelRequest request);
}
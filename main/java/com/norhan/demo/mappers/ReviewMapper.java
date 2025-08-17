package com.norhan.demo.mappers;

import com.norhan.demo.dtos.ReviewDto;
import com.norhan.demo.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "user.id", target = "userId")
    ReviewDto toDto(Review review);
}

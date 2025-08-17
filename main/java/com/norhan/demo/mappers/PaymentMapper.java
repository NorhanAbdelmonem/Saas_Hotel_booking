package com.norhan.demo.mappers;

import com.norhan.demo.dtos.PaymentDto;
import com.norhan.demo.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "booking.id", target = "bookingId")
    PaymentDto toDto(Payment payment);
}
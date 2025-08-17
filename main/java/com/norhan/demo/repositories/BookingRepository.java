package com.norhan.demo.repositories;



import com.norhan.demo.entities.Booking;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

    List<Booking> findByRoomId(Long roomId);

    List<Booking> findByHotel_Manager_Id(Long managerId);

    List<Booking> findByRoomHotelManagerId(Long managerId, Sort sort);
}


package com.norhan.demo.repositories;


import com.norhan.demo.entities.Hotel;
import com.norhan.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByManagerId(Long managerId);
    List<Hotel> findByManager(User manager);
    boolean existsByNameAndManagerId(String name, Long managerId);

}
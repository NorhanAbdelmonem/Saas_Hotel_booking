package com.norhan.demo.hotel;


import com.norhan.demo.auth.AuthService;
import com.norhan.demo.common.AccessDeniedException;
import com.norhan.demo.dtos.HotelDto;
import com.norhan.demo.entities.Hotel;
import com.norhan.demo.entities.User;
import com.norhan.demo.hotel.CreateHotelRequest;
import com.norhan.demo.hotel.HotelNotFoundException;
import com.norhan.demo.mappers.HotelMapper;
import com.norhan.demo.repositories.HotelRepository;
import com.norhan.demo.repositories.UserRepository;
import com.norhan.demo.user.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@AllArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final HotelMapper hotelMapper;
    private final AuthService authService;




    @Transactional(readOnly = true)
    public List<HotelDto> getAllPublicHotels(String sortBy) {
        if (!(sortBy.equals("name") || sortBy.equals("location"))) sortBy = "name";
        return hotelRepository.findAll(Sort.by(sortBy))
                .stream().map(hotelMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public HotelDto getHotel(Long id) {
        Hotel h = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException(id));
        return hotelMapper.toDto(h);
    }

    @Transactional(readOnly = true)
    public List<Hotel> getHotelsByManager(User manager) {
        return hotelRepository.findByManager(manager);
    }

    @Transactional
    public HotelDto createMyHotel(CreateHotelRequest request) {
        User manager = authService.getCurrentUser();

         if (hotelRepository.existsByNameAndManagerId(request.getName(), manager.getId())) {
            throw new IllegalArgumentException("You already have a hotel with the same name");}

        Hotel hotel = hotelMapper.toEntity(request);
        hotel.setManager(manager);
        Hotel saved = hotelRepository.save(hotel);
        return hotelMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<HotelDto> getMyHotels() {
        Long managerId = authService.getCurrentUserId();
        return hotelRepository.findByManagerId(managerId)
                .stream().map(hotelMapper::toDto).toList();
    }

    @Transactional
    public HotelDto update(Long id, UpdateHotelRequest request) {
        User manager = authService.getCurrentUser();
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        if (!hotel.getManager().getId().equals(manager.getId())) {
            throw new AccessDeniedException("You are not allowed to update this hotel");
        }

         if (request.getName() != null && !request.getName().equalsIgnoreCase(hotel.getName())) {
             var duplicate = hotelRepository.existsByNameAndManagerId(request.getName(), manager.getId());
            if (duplicate) throw new IllegalArgumentException("You already have a hotel with the same name");
         }
        hotelMapper.update(hotel, request);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }

    @Transactional
    public void delete(Long id) {
        User manager = authService.getCurrentUser();
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));

        if (!hotel.getManager().getId().equals(manager.getId())) {
            throw new AccessDeniedException("You are not allowed to delete this hotel");
        }
        hotelRepository.delete(hotel);
    }


    public Hotel addHotel(Hotel hotel, User manager) {
        hotel.setManager(manager);
        return hotelRepository.save(hotel);
    }





}

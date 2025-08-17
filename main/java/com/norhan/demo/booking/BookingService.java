package com.norhan.demo.booking;

import com.norhan.demo.auth.AuthService;
import com.norhan.demo.common.AccessDeniedException;
import com.norhan.demo.dtos.BookingDto;
import com.norhan.demo.entities.Booking;
import com.norhan.demo.entities.Room;
import com.norhan.demo.entities.User;
import com.norhan.demo.mappers.BookingMapper;
import com.norhan.demo.repositories.BookingRepository;
import com.norhan.demo.repositories.RoomRepository;
import com.norhan.demo.repositories.UserRepository;
import com.norhan.demo.room.RoomNotFoundException;
import com.norhan.demo.user.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final AuthService authService;


    @Transactional
    public BookingDto createBooking(CreateBookingRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(request.getRoomId()));

        if (!room.getIsAvailable()) {
            throw new RoomNotFoundException(request.getRoomId());
        }

        User user = authService.getCurrentUser();

        Booking booking = bookingMapper.toEntity(request);
        booking.setRoom(room);
        booking.setUser(user);
        room.setIsAvailable(false);
        roomRepository.save(room);

        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingDto> getMyBookings() {
        Long customerId = authService.getCurrentUserId();
        return bookingRepository.findByUserId(customerId)
                .stream()
                .sorted(Comparator.comparing(Booking::getCreatedAt).reversed())
                .map(bookingMapper::toDto)
                .toList();
    }



    @Transactional(readOnly = true)
    public List<BookingDto> getAllBookings(String sortBy) {
        if (!Set.of("createdAt", "checkInDate", "guestName").contains(sortBy)) {
            sortBy = "createdAt";
        }

        return bookingRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookingDto getBooking(Long id) {
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
        Long me = authService.getCurrentUserId();
        if (!booking.getUser().getId().equals(me)) {
            throw new AccessDeniedException("You are not allowed to view this booking");
        }
        return bookingMapper.toDto(booking);
    }


    @Transactional
    public BookingDto updateBooking(Long id, UpdateBookingRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));

        bookingMapper.update(booking, request);

        if (request.getRoomId() != null && !request.getRoomId().equals(booking.getRoom().getId())) {
            Room newRoom = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(request.getRoomId()));

            if (!newRoom.getIsAvailable()) {
                throw new RoomNotFoundException(request.getRoomId());
            }
            booking.getRoom().setIsAvailable(true);

            newRoom.setIsAvailable(false);
            booking.setRoom(newRoom);
        }
        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }




    public void deleteBooking(Long id) {
        var me = authService.getCurrentUserId();
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
        if (!booking.getUser().getId().equals(me)) {
            throw new AccessDeniedException("You are not allowed to delete this booking");
        }
        Room room = booking.getRoom();
        room.setIsAvailable(true);
        roomRepository.save(room);

        bookingRepository.delete(booking);
    }


}
package com.norhan.demo.review;



import com.norhan.demo.dtos.ReviewDto;
import com.norhan.demo.entities.Hotel;
import com.norhan.demo.entities.Review;
import com.norhan.demo.entities.User;
import com.norhan.demo.hotel.HotelNotFoundException;
import com.norhan.demo.mappers.ReviewMapper;
import com.norhan.demo.repositories.HotelRepository;
import com.norhan.demo.repositories.ReviewRepository;
import com.norhan.demo.repositories.UserRepository;
import com.norhan.demo.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    public ReviewDto create(Long hotelId, Long userId, ReviewDto dto) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(hotelId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Review review = Review.builder()
                .hotel(hotel)
                .user(user)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();

        review = reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> listByHotel(Long hotelId) {
        return reviewRepository.findByHotelId(hotelId)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReviewDto get(Long id) {
        Review r = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        return reviewMapper.toDto(r);
    }

    @Transactional
    public ReviewDto update(Long id, ReviewDto dto) {
        Review r = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        // بنحدّث بس حقول المراجعة، مش العلاقة
        r.setRating(dto.getRating());
        r.setComment(dto.getComment());
        r = reviewRepository.save(r);
        return reviewMapper.toDto(r);
    }

    @Transactional
    public void delete(Long id) {
        if (!reviewRepository.existsById(id)) throw new ReviewNotFoundException(id);
        reviewRepository.deleteById(id);
    }
}

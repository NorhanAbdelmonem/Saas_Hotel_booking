package com.norhan.demo.controllers;



import com.norhan.demo.dtos.ReviewDto;
import com.norhan.demo.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.get(id));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<ReviewDto>> byHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(reviewService.listByHotel(hotelId));
    }


    @PostMapping("/hotel/{hotelId}/user/{userId}")
    public ResponseEntity<ReviewDto> create(
            @PathVariable Long hotelId,
            @PathVariable Long userId,
            @Valid @RequestBody ReviewDto dto) {
        return ResponseEntity.ok(reviewService.create(hotelId, userId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> update(@PathVariable Long id, @Valid @RequestBody ReviewDto dto) {
        return ResponseEntity.ok(reviewService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


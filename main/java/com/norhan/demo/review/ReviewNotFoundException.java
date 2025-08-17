package com.norhan.demo.review;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(Long id) {
        super("Could not find review with id " + id);
    }
}

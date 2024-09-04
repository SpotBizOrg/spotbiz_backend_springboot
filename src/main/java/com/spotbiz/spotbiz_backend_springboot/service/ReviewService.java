package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.ReviewRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Review;

import java.util.List;

public interface ReviewService {
    Review saveReview(ReviewRequestDto review);
    List<Review> getAllReviews(String email);
    ReviewRequestDto setData(ReviewRequestDto review, String email);
}

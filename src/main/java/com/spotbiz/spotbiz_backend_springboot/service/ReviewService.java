package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.*;
import com.spotbiz.spotbiz_backend_springboot.entity.Review;

import java.util.List;

public interface ReviewService {
    Review saveReview(ReviewRequestDto review);
    List<ReviewDto> getAllReviews(String email);
    ReviewRequestDto setData(ReviewRequestDto review, String email);
    double getAverageRating(Integer businessId);
    ReviewReportResponseDto markReported(Integer reviewId);
    List<ReviewReportResponseDto> getReportedReviews();
    RatingStatsDto getStatistics(Integer businessId);
    ReviewStatsDto getAllStatistics(Integer businessId);
}

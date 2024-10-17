package com.spotbiz.spotbiz_backend_springboot.api;


import com.spotbiz.spotbiz_backend_springboot.dto.ReviewRequestDto;
import com.spotbiz.spotbiz_backend_springboot.service.impl.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review-report")
public class ReviewReportController {

    @Autowired
    private ReviewServiceImpl reviewService;

    @PutMapping("/mark/{reviewId}")
    public ResponseEntity<?> markReviewReport(@PathVariable Integer reviewId) {
        try {
            return ResponseEntity.ok(reviewService.markReported(reviewId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to mark review as reported: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getReportedReviews() {
        try {
            return ResponseEntity.ok(reviewService.getReportedReviews());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to get reported reviews: " + e.getMessage());
        }
    }
}

package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.ReviewRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Review;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.spotbiz.spotbiz_backend_springboot.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;



    @PostMapping("/create/{email}")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDto review, @PathVariable String email) {
            review = reviewService.setData(review, email);
            Review savedReview = reviewService.saveReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);

    }

    @GetMapping("/all/{email}")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable String email) {
        try {
            List<Review> reviews = reviewService.getAllReviews(email);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

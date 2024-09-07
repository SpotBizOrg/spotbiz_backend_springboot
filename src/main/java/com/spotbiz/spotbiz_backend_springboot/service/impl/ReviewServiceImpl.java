package com.spotbiz.spotbiz_backend_springboot.service.impl;
import com.spotbiz.spotbiz_backend_springboot.dto.ReviewRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Review;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.exception.DuplicateReviewException;
import com.spotbiz.spotbiz_backend_springboot.mapper.ReviewMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.ReviewRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.ReviewService;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserService userService;

    @Override
    public Review saveReview(ReviewRequestDto reviewDto) {
        try {

            Review review = reviewMapper.toReviewEntity(reviewDto);
            boolean reviewExists = reviewRepo.existsByUserAndBusiness(review.getUser(), review.getBusiness());
            if (reviewExists) {
                throw new DuplicateReviewException("You have already reviewed this business.");
            }
            return reviewRepo.save(review);

        } catch (DuplicateReviewException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the review", e);
        }
    }


    @Override
    public List<Review> getAllReviews(String email) {

        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return reviewRepo.findAll();
    }

    public ReviewRequestDto setData(ReviewRequestDto review, String email) {
        review.setRating(5);
        Integer userId = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")).getUserId();
        review.setUserId(userId);
        return review;
    }
}
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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;


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

    private static final String SENTIMENT_GET_API_URL = "http://localhost:8000/sentiment";

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
//        review.setRating(5);
        try {
            review.setRating(generateRating(review));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate rating", e);
        }
        Integer userId = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")).getUserId();
        review.setUserId(userId);
        return review;
    }

    public int generateRating(ReviewRequestDto review) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(SENTIMENT_GET_API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            // Construct the JSON body
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("text", review.getDescription()); // Add the description with the keyword "text"

            StringEntity entity = new StringEntity(jsonBody.toString());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseBody);
                return jsonResponse.getInt("score");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getAverageRating(Integer businessId) {
        try {

            //            System.out.println("Average rating: " + averageRating);
            return reviewRepo.getAverageRatingByBusiness(businessId);
//            return reviewRepo.getAverageRatingByBusiness(businessId);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException("Failed to get average rating", e);
        }
    }

    public int businessReviewCount(Integer businessId) {
        try {
            return reviewRepo.countByBusiness(businessId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get business review count", e);
        }
    }
}

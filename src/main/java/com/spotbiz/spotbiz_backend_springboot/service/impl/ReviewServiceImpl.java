package com.spotbiz.spotbiz_backend_springboot.service.impl;
import com.spotbiz.spotbiz_backend_springboot.dto.ReviewReportResponseDto;
import com.spotbiz.spotbiz_backend_springboot.dto.ReviewRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Review;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.exception.DuplicateReviewException;
import com.spotbiz.spotbiz_backend_springboot.mapper.ReviewMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



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
    @Autowired
    private BusinessRepo businessRepo;

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
        Business business = businessRepo.findByUserUserId(user.getUserId());
        return reviewRepo.findByBusiness(business);
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

    @Override
    public ReviewReportResponseDto markReported(Integer reviewId) {
        try {
            Review review = reviewRepo.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found for reviewId: " + reviewId));
            review.setStatus("REPORTED");
            Review updatedReview = reviewRepo.save(review);
            return reviewMapper.toReviewReportResponseDto(updatedReview);
        } catch (Exception e) {
            throw new RuntimeException("Failed to mark review as reported", e);
        }
    }

    @Override
    public List<ReviewReportResponseDto> getReportedReviews() {
        try {
            List<Review> reportedList = reviewRepo.findReviewsByStatus("REPORTED");
            return reportedList.stream()
                    .map(reviewMapper::toReviewReportResponseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get reported reviews", e);
        }
    }

    public int businessReviewCount(Integer businessId) {
        try {
            return reviewRepo.countByBusiness(businessId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get business review count", e);
        }
    }

    public Review findLatestBusinessReview(Integer businessId) {
        try {
            Optional<Review> latestReviewOpt = reviewRepo.findLatestBusinessReview(businessId);
            return latestReviewOpt.orElse(null); // Or throw a custom exception if needed
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException("Failed to get latest business review", e);
        }
    }

    public Review markAction(Integer reviewId, String action) {
        try {
            Review review = reviewRepo.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found for reviewId: " + reviewId));

            if (action.equals("DELETE")) {
                reviewRepo.delete(review);
                return review;
            } else if (action.equals("KEEP")) {
                review.setStatus(null);
                review = reviewRepo.save(review);
                return review;
            } else {
                throw new IllegalArgumentException("Invalid action: " + action);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to mark review as reported", e);
        }
    }
}

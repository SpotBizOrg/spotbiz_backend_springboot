package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDataDto;
import com.spotbiz.spotbiz_backend_springboot.dto.ReviewRequestDto;
import com.spotbiz.spotbiz_backend_springboot.dto.WeeklyScheduleDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Review;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BusinessDataServiceImpl implements BusinessDataService {

    @Autowired
    private BusinessRepo businessRepository;

    @Autowired
    private ReviewServiceImpl reviewServiceImpl;

    @Autowired
    private OpeningHoursServiceImpl openingHoursServiceImpl;

    @Override
    public BusinessDataDto getBusinessData(Integer businessId) {
        try {
            Business business = businessRepository.findById(businessId).get();
            BusinessDataDto businessDataDto = setBusinessData(business);
            return businessDataDto;

        } catch (Exception e) {
            throw new RuntimeException("Business not found");
        }
    }

    private BusinessDataDto setBusinessData(Business business){
        BusinessDataDto businessDataDto = new BusinessDataDto();
        businessDataDto.setBusinessId(business.getBusinessId());
        businessDataDto.setName(business.getName());
        businessDataDto.setAddress(business.getAddress());
        businessDataDto.setLogo(business.getLogo());
        businessDataDto.setDescription(business.getDescription());
        businessDataDto.setEmail(business.getUser().getEmail());
        businessDataDto.setPhone(business.getContactNo());
        businessDataDto.setAvgRating(getAvgRatings(business.getBusinessId()));
        businessDataDto.setTags(getBusinessCategory(business.getBusinessId()));
        businessDataDto.setWeeklySchedule(getOpeningHours(business.getUser().getEmail()));
        businessDataDto.setReviewCount(reviewCount(business.getBusinessId()));
        businessDataDto.setLatestReview(getLatestReview(business.getBusinessId()));

        return businessDataDto;
    }

    private Double getAvgRatings(Integer businessId) {
        try {

            return reviewServiceImpl.getAverageRating(businessId);

        } catch (Exception e) {
            throw new RuntimeException("Failed to get average ratings: " + e.getMessage());
        }
    }

    // this function is used to extract the category list from the keywords json string
    private List<String> getBusinessCategory(Integer businessId) {
        try {
            String res =  businessRepository.getBusinessCategory(businessId);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<String>> keywordMap = objectMapper.readValue(res, new TypeReference<Map<String, List<String>>>() {
            });

            // Extract and return the list of keywords
            return keywordMap.get("keywords");


        } catch (Exception e) {
            throw new RuntimeException("Failed to get category name: " + e.getMessage());
        }
    }

    private WeeklyScheduleDto getOpeningHours(String email){
        try {
            return openingHoursServiceImpl.getOpeningHours(email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get opening hours: " + e.getMessage());
        }
    }

    private int reviewCount(Integer businessId) {
        try {
            return reviewServiceImpl.businessReviewCount(businessId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get review count: " + e.getMessage());
        }
    }

    private ReviewRequestDto getLatestReview(Integer businessId) {
        try {
            Review review =  reviewServiceImpl.findLatestBusinessReview(businessId);

            ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
            reviewRequestDto.setDescription(review.getDescription());
            reviewRequestDto.setRating(review.getRating());
            reviewRequestDto.setTitle(review.getUser().getName()); // set reviewer's name instead of title
            reviewRequestDto.setBusinessId(review.getBusiness().getBusinessId());
            reviewRequestDto.setUserId(review.getUser().getUserId());
            reviewRequestDto.setDate(review.getDate());
            return reviewRequestDto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get latest review: " + e.getMessage());
        }
    }
}

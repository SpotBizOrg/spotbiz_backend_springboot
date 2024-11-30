package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.*;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Review;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessDataService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BusinessDataServiceImpl implements BusinessDataService {


    private final BusinessRepo businessRepository;


    private final ReviewServiceImpl reviewServiceImpl;


    private final OpeningHoursServiceImpl openingHoursServiceImpl;

    private final PackageServiceImpl packageServiceImpl;

    private final SubscribeServiceImpl subscribeServiceImpl;

    private final BusinessBadgeServiceImpl businessBadgeServiceImpl;

    public BusinessDataServiceImpl(BusinessRepo businessRepository, ReviewServiceImpl reviewServiceImpl, OpeningHoursServiceImpl openingHoursServiceImpl, PackageServiceImpl packageServiceImpl, SubscribeServiceImpl subscribeServiceImpl, BusinessBadgeServiceImpl businessBadgeServiceImpl) {
        this.businessRepository = businessRepository;
        this.reviewServiceImpl = reviewServiceImpl;
        this.openingHoursServiceImpl = openingHoursServiceImpl;
        this.packageServiceImpl = packageServiceImpl;
        this.subscribeServiceImpl = subscribeServiceImpl;
        this.businessBadgeServiceImpl = businessBadgeServiceImpl;
    }


    @Override
    public BusinessDataDto getBusinessData(Integer businessId, Integer clientId) {
        try {


            Optional<Business> optionalBusiness = businessRepository.findById(businessId);

            if (optionalBusiness.isEmpty()) {
                return null;
            }

            Business business = optionalBusiness.get();

            BusinessDataDto businessDataDto = setBusinessData(business, clientId);
            return businessDataDto;

        } catch (Exception e) {
            throw new RuntimeException("Business not found");
        }
    }

    private BusinessDataDto setBusinessData(Business business, Integer clientId) {
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
        businessDataDto.setPkg(getSubscriptionPackage(business.getBusinessId()));
        businessDataDto.setSubscribed(checkSubscribed(clientId, business.getBusinessId()));
        businessDataDto.setBusinessBadgeDto(getLatestBadge(business));

        return businessDataDto;
    }

    private BusinessBadgeDto getLatestBadge(Business business) {
        try {
           BusinessBadgeDto businessBadgeDto = businessBadgeServiceImpl.getLatestBadge(business);
           return businessBadgeDto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get latest badge: " + e.getMessage());
        }
    }

    private boolean checkSubscribed(Integer userId, Integer businessId) {
        try {
            return subscribeServiceImpl.checkSubscription(userId, businessId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check subscription: " + e.getMessage());
        }
    }

    public PackageDto getSubscriptionPackage(Integer businessId) {
        try {
            PackageDto subscriptionPackage = packageServiceImpl.getPackageByBusinessId(businessId);
            return subscriptionPackage;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get subscription billing: " + e.getMessage());
        }
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

            if (review == null) {
                return null;
            }

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

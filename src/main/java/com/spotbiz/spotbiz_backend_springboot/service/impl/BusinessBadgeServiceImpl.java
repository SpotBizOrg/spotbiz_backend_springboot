package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessBadgeDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessBadge;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessBadgeRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessBadgeServiceImpl implements BusinessBadgeService {

    @Autowired
    private BusinessBadgeRepo businessBadgeRepo;

    @Autowired
    private BusinessRepo businessRepo;
    @Override
    public BusinessBadgeDto getNewBadge() {
        try {
            // Assuming getBusinessForBadge() returns a single row in a list
            List<Object[]> resultList = businessBadgeRepo.getBusinessForBadge();
            if (resultList != null && !resultList.isEmpty()) {
                Object[] result = resultList.get(0); // Get the first row
                Integer businessId = (Integer) result[0]; // Extract the business_id
                BigDecimal avgRatingBigDecimal = (BigDecimal) result[1]; // Extract as BigDecimal
                Double avgRating = avgRatingBigDecimal.doubleValue(); // Convert to Double

                // Fetch the business entity
                Business chosenBusiness = businessRepo.findById(businessId)
                        .orElseThrow(() -> new IllegalArgumentException("Business not found"));

//                // Create and save a new badge
//                BusinessBadge newBusinessBadge = new BusinessBadge(0, chosenBusiness, LocalDateTime.now(), avg_rating);
//                newBusinessBadge = businessBadgeRepo.save(newBusinessBadge);

                // Construct and return the DTO
                BusinessBadgeDto badgeDto = new BusinessBadgeDto();
                badgeDto.setBusinessId(chosenBusiness.getBusinessId());
                badgeDto.setBusinessName(chosenBusiness.getName());
                badgeDto.setIssuedDate(LocalDateTime.now());
                badgeDto.setRating(avgRating);
                return badgeDto;
            } else {
                throw new IllegalStateException("No business found for badge allocation");
            }
        } catch (Exception e) {
            // Handle exceptions and log errors
            e.printStackTrace();
            throw new RuntimeException("Error fetching business for badge", e);
        }
    }

    public BusinessBadgeDto saveNewBadge(BusinessBadgeDto businessBadgeDto){
        int businessId = businessBadgeDto.getBusinessId();
        double avgRating = businessBadgeDto.getRating();
//        System.out.println(businessId);
        // Fetch the business entity
        Business chosenBusiness = businessRepo.findById(businessId)
                .orElseThrow(() -> new IllegalArgumentException("Business not found"));

        System.out.println(chosenBusiness.getBusinessId());
        BusinessBadge newBusinessBadge = new BusinessBadge(0, chosenBusiness, LocalDateTime.now(), avgRating);

        try {
            BusinessBadge savedBadge = businessBadgeRepo.save(newBusinessBadge);
            return new BusinessBadgeDto(
                    savedBadge.getBadgeId(),
                    savedBadge.getBusiness().getBusinessId(),
                    savedBadge.getBusiness().getName(),
                    savedBadge.getIssuedDate(),
                    savedBadge.getAvgRating()
            );
        }catch (Exception e) {
            // Handle exceptions and log errors
            e.printStackTrace();
            throw new RuntimeException("Error saving business badge", e);
        }
    }



    @Override
    public List<BusinessBadgeDto> getPastBadges() {

        LocalDateTime sixMonthsAgo = LocalDateTime.now().minus(6, ChronoUnit.MONTHS);

        try {
            List<BusinessBadge> pastSixBadges = businessBadgeRepo.findBusinessBadgesByIssuedDateBetweenOrderByIssuedDateAsc(sixMonthsAgo, LocalDateTime.now());
            List<BusinessBadgeDto> businessBadgeDtoList = new ArrayList<>();

            for (BusinessBadge businessBadge: pastSixBadges) {
                BusinessBadgeDto businessBadgeDto = new BusinessBadgeDto(businessBadge.getBadgeId(), businessBadge.getBusiness().getBusinessId(), businessBadge.getBusiness().getName(), businessBadge.getIssuedDate(), businessBadge.getAvgRating());
                businessBadgeDtoList.add(businessBadgeDto);
            }

            return businessBadgeDtoList;
        }catch (Exception e) {
            // Handle exceptions and log errors
            e.printStackTrace();
            throw new RuntimeException("Error fetching issued badges details", e);
        }
    }

    @Override
    public BusinessBadgeDto getLatestBadge(Business business) {
        try {
            Optional<BusinessBadge> optionalBusinessBadge = businessBadgeRepo.getLatestBadge(business.getBusinessId());

            if (optionalBusinessBadge.isEmpty()){
                return null;
            }

            BusinessBadge businessBadge = optionalBusinessBadge.get();
            BusinessBadgeDto businessBadgeDto = new BusinessBadgeDto(
                    businessBadge.getBadgeId(),
                    businessBadge.getBusiness().getBusinessId(),
                    businessBadge.getBusiness().getName(),
                    businessBadge.getIssuedDate(),
                    businessBadge.getAvgRating()
            );
            return businessBadgeDto;

        }catch (Exception e) {
            // Handle exceptions and log errors
            e.printStackTrace();
            throw new RuntimeException("Error fetching issued badges details", e);
        }
    }
}

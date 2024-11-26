package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessBadgeDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessBadge;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessBadgeRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

                // Fetch the business entity
                Business chosenBusiness = businessRepo.findById(businessId)
                        .orElseThrow(() -> new IllegalArgumentException("Business not found"));

                // Create and save a new badge
                BusinessBadge newBusinessBadge = new BusinessBadge(0, chosenBusiness, LocalDateTime.now());
                newBusinessBadge = businessBadgeRepo.save(newBusinessBadge);

                // Construct and return the DTO
                BusinessBadgeDto badgeDto = new BusinessBadgeDto();
                badgeDto.setBusinessId(chosenBusiness.getBusinessId());
                badgeDto.setBusinessName(chosenBusiness.getName());
                badgeDto.setIssuedDate(newBusinessBadge.getIssuedDate());
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



    @Override
    public List<BusinessBadgeDto> getPastBadges() {

        LocalDateTime sixMonthsAgo = LocalDateTime.now().minus(6, ChronoUnit.MONTHS);

        try {
            List<BusinessBadge> pastSixBadges = businessBadgeRepo.findBusinessBadgesByIssuedDateBetweenOrderByIssuedDateAsc(sixMonthsAgo, LocalDateTime.now());
            List<BusinessBadgeDto> businessBadgeDtoList = new ArrayList<>();

            for (BusinessBadge businessBadge: pastSixBadges) {
                BusinessBadgeDto businessBadgeDto = new BusinessBadgeDto(businessBadge.getBadgeId(), businessBadge.getBusiness().getBusinessId(), businessBadge.getBusiness().getName(), businessBadge.getIssuedDate());
                businessBadgeDtoList.add(businessBadgeDto);
            }

            return businessBadgeDtoList;
        }catch (Exception e) {
            // Handle exceptions and log errors
            e.printStackTrace();
            throw new RuntimeException("Error fetching issued badges details", e);
        }
    }
}

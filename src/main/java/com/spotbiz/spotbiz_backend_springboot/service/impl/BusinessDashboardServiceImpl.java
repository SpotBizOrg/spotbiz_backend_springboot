package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessBadgeDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDashboardDto;
import com.spotbiz.spotbiz_backend_springboot.dto.PackageDto;
import com.spotbiz.spotbiz_backend_springboot.dto.SubscribeDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessClicks;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessClicksRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.SubscribeRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessDashboardServiceImpl implements BusinessDashboardService {

    @Autowired
    private BusinessRepo businessRepo;

    @Autowired
    private BusinessClicksRepo businessClicksRepo;

    @Autowired
    private SubscribeServiceImpl subscribeService;

    @Autowired
    private PackageServiceImpl packageService;

    @Autowired
    private BusinessBadgeServiceImpl businessBadgeService;


    @Override
    public BusinessDashboardDto getBusinessDashboardData(User user) {

        try {
            Business business = businessRepo.findByUserUserId(user.getUserId());

            BusinessDashboardDto businessDashboardDto = new BusinessDashboardDto();
            businessDashboardDto.setBusinessId(business.getBusinessId());
            businessDashboardDto.setLogo(business.getLogo());
            businessDashboardDto.setEmail(user.getEmail());
            businessDashboardDto.setClickCount(getClickCount(business.getBusinessId()));
            businessDashboardDto.setSubscriberCount(countSubscribers(business.getBusinessId()));
            businessDashboardDto.setPkg(getSubscriptionPackage(business.getBusinessId()));
            businessDashboardDto.setSubscribeList(getSubscriberList(business.getBusinessId()));
            businessDashboardDto.setBusinessBadgeDto(getLatestBadge(business));
            return businessDashboardDto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve business dashboard data");
        }


    }

    private BusinessBadgeDto getLatestBadge(Business business){
        try {
            return businessBadgeService.getLatestBadge(business);
        }catch (Exception e) {
            throw new RuntimeException("Failed to get business badge: " + e.getMessage());
        }
    }

    private PackageDto getSubscriptionPackage(int businessId){
        try {
            PackageDto subscriptionPackage = packageService.getPackageByBusinessId(businessId);
            return subscriptionPackage;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get subscription billing: " + e.getMessage());
        }
    }

    private List<SubscribeDto> getSubscriberList(int businessId){
        try{
            List<SubscribeDto> subscribeDtoList = subscribeService.getSubscribers(businessId);
            return subscribeDtoList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve subscriber list");
        }
    }
    private int countSubscribers(int businessId){
        try{
            int subscriberCount = subscribeService.getSubscriberCount(businessId);
            return subscriberCount;
        } catch (Exception e) {
            throw new RuntimeException("failed to retrieve subscribe data", e);
        }
    }

    private String getLogo(int businessId){
        try {
            String logo = businessRepo.getBusinessLogo(businessId);
            System.out.println(logo);
            return logo;
        }catch (Exception e){
            throw new RuntimeException("failed to retrieve business logo", e);
        }
    }

    private int getClickCount(int businessId){
        try {
            Optional<BusinessClicks> businessClicks = businessClicksRepo.findByBusinessId(businessId);

            if (businessClicks.isEmpty()){
                return 0;
            }
            BusinessClicks businessClicks1 = businessClicks.get();
            return businessClicks1.getClicks();

        }catch (Exception e){
            throw new RuntimeException("failed to retrieve business logo", e);
        }
    }

}

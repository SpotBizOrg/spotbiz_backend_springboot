package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDashboardDto;
import com.spotbiz.spotbiz_backend_springboot.dto.PackageDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessClicks;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessClicksRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.SubscribeRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public BusinessDashboardDto getBusinessDashboardData(Integer businessId) {

        try {
            BusinessDashboardDto businessDashboardDto = new BusinessDashboardDto();
            businessDashboardDto.setBusinessId(businessId);
            businessDashboardDto.setLogo(getLogo(businessId));
            businessDashboardDto.setClickCount(getClickCount(businessId));
            businessDashboardDto.setSubscriberCount(countSubscribers(businessId));
            businessDashboardDto.setPkg(getSubscriptionPackage(businessId));
            return businessDashboardDto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve business dashboard data");
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

package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.LandingPageDto;
import com.spotbiz.spotbiz_backend_springboot.service.LandingPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandingPageServiceImpl implements LandingPageService {

    @Autowired
    private AdminDashboardServiceImpl adminDashboardService;
    @Override
    public LandingPageDto getLandingPageData() {

        try {
            LandingPageDto landingPageDto = new LandingPageDto();
            landingPageDto.setCustomerCount(adminDashboardService.getCustomerCount());
            landingPageDto.setBusinessCount(adminDashboardService.getBusinessCount());
            landingPageDto.setCategoryCount((adminDashboardService.getBusinessCategories()).size());
            return landingPageDto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch landing page data", e);
        }

    }
}

package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.AdminDashboardDataDto;
import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionBillingDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Role;
import com.spotbiz.spotbiz_backend_springboot.entity.Status;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionBillingServiceImpl subscriptionBillingService;

    @Autowired
    private BusinessCategoryServiceImpl businessCategoryService;
    @Override
    public AdminDashboardDataDto getDashboardData() {
        try{
            AdminDashboardDataDto dashboardDataDto = new AdminDashboardDataDto();
            dashboardDataDto.setCustomerCount(getCustomerCount());
            dashboardDataDto.setBusinessCount(getBusinessCount());
            dashboardDataDto.setTotalRevenue(getRevenue());
            dashboardDataDto.setBillingList(getBillingData());
            dashboardDataDto.setBusinessCategoryCount(getBusinessCategories());

            return dashboardDataDto;

        } catch (Exception e) {
                throw new RuntimeException("Failed to fetch dashboard data", e);
        }
    }

    private int getCustomerCount(){
        try{
            int customerCount = userRepo.countUsersByRoleAndStatus(Role.CUSTOMER, Status.APPROVED);
            return customerCount;

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve customer count", e);
        }
    }

    private int getBusinessCount(){
        try{
            int businessCount = userRepo.countUsersByRoleAndStatus(Role.BUSINESS_OWNER, Status.APPROVED);
            return businessCount;

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve business count", e);
        }
    }

    private double getRevenue(){
        try {
            double totalRevenue = subscriptionBillingService.getTotalBillings();
            return totalRevenue;
        }catch (Exception e) {
            throw new RuntimeException("Failed to retrieve billing data", e);
        }
    }

    private List<SubscriptionBillingDto> getBillingData(){
        try {
            List<SubscriptionBillingDto> billingDtoList = subscriptionBillingService.getPastMonthBillings();
            return billingDtoList;
        }catch (Exception e) {
            throw new RuntimeException("Failed to retrieve billings", e);
        }
    }

    private Map<String, Long> getBusinessCategories(){
        try {
            Map<String, Long> businessCategoryData = businessCategoryService.getBusinessCountByCategory();
            return businessCategoryData;
        }catch (Exception e) {
            throw new RuntimeException("Failed to retrieve business category data", e);
        }
    }
}

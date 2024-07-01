package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessVerifyServiceImpl implements BusinessVerifyService {

    private final BusinessRepo businessRepo;

    @Autowired
    public BusinessVerifyServiceImpl(BusinessRepo businessRepo) {
        this.businessRepo = businessRepo;
    }

    @Override
    public List<Business> getUnverifiedBusinesses() {
        return businessRepo.findByStatus("PENDING");
    }

    @Override
    public Business verfiyBusiness(Integer businessId) {
        try {
            Business business = businessRepo.findById(businessId).orElseThrow(() -> new Exception("Business not found"));
            business.setStatus("VERIFIED");
            return businessRepo.save(business);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to verify business: " + ex.getMessage());
        }
    }
}

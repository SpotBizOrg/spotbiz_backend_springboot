package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Status;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessVerifyServiceImpl implements BusinessVerifyService {

    private final BusinessRepo businessRepo;
    private final UserRepo userRepo;

    @Autowired
    public BusinessVerifyServiceImpl(BusinessRepo businessRepo, UserRepo userRepo) {
        this.businessRepo = businessRepo;
        this.userRepo = userRepo;
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
            System.out.println(business.getUser().getUserId());
            User user = userRepo.findById(business.getUser().getUserId()).orElseThrow(() -> new Exception("User not found"));
            user.setStatus(Status.APPROVED);

            if (userRepo.save(user) == null) {
                throw new Exception("Failed to verify business");
            }
            return businessRepo.save(business);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to verify business: " + ex.getMessage());
        }
    }
}

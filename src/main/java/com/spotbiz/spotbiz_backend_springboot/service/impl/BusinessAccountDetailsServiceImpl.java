package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.entity.BusinessAccountDetails;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessAccountDetailsRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessAccountDetailsService;
import org.springframework.stereotype.Service;

@Service
public class BusinessAccountDetailsServiceImpl implements BusinessAccountDetailsService {

    private final BusinessAccountDetailsRepo businessAccountDetailsRepo;
    private final BusinessRepo businessRepo;

    public BusinessAccountDetailsServiceImpl(BusinessAccountDetailsRepo businessAccountDetailsRepo, BusinessRepo businessRepo) {
        this.businessAccountDetailsRepo = businessAccountDetailsRepo;
        this.businessRepo = businessRepo;
    }

    @Override
    public int insertBusinessAccountDetails(BusinessAccountDetails businessAccountDetails) {
        return businessAccountDetailsRepo.save(businessAccountDetails).getId();
    }

    @Override
    public BusinessAccountDetails getBusinessAccountDetails(int id) {
        return businessAccountDetailsRepo.getReferenceByBusiness(businessRepo.getReferenceById(id));
    }
}

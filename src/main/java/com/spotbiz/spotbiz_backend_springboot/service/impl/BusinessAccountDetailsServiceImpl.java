package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessAccountDetails;
import com.spotbiz.spotbiz_backend_springboot.entity.Reimbursements;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessAccountDetailsRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.ReimbursementRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessAccountDetailsService;
import com.spotbiz.spotbiz_backend_springboot.service.ReimbursementService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessAccountDetailsServiceImpl implements BusinessAccountDetailsService {

    private final BusinessAccountDetailsRepo businessAccountDetailsRepo;
    private final BusinessRepo businessRepo;
    private final ReimbursementRepo reimbursementRepo;

    public BusinessAccountDetailsServiceImpl(BusinessAccountDetailsRepo businessAccountDetailsRepo, BusinessRepo businessRepo, ReimbursementRepo reimbursementRepo) {
        this.businessAccountDetailsRepo = businessAccountDetailsRepo;
        this.businessRepo = businessRepo;
        this.reimbursementRepo = reimbursementRepo;
    }

    @Override
    public int insertBusinessAccountDetails(BusinessAccountDetails businessAccountDetails) {
        return businessAccountDetailsRepo.save(businessAccountDetails).getId();
    }

    @Override
    public BusinessAccountDetails getBusinessAccountDetails(int id) {
        Business business = businessRepo.findByBusinessId(id);
        if(business == null) {
            return null;
        }
        return businessAccountDetailsRepo.getReferenceByBusiness(business);
    }

    @Override
    public BusinessAccountDetails getBusinessAccountDetailsByReimburseId(int id) {
        Optional<Reimbursements> reimbursement = reimbursementRepo.findById(id);
        if(reimbursement.isEmpty()) {
            return null;
        }
        Business business = businessRepo.findByBusinessId(reimbursement.get().getBusiness().getBusinessId());
        if(business == null) {
            return null;
        }
        return businessAccountDetailsRepo.getReferenceByBusiness(business);
    }
}
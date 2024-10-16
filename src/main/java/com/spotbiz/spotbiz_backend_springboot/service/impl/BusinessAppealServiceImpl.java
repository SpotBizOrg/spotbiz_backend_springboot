package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAppealDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessAppeal;
import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;
import com.spotbiz.spotbiz_backend_springboot.exception.ResourceNotFoundException;
import com.spotbiz.spotbiz_backend_springboot.mapper.BusinessAppealMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessAppealRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.ReportedBusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessAppealService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessAppealServiceImpl implements BusinessAppealService {

    private final BusinessAppealMapper businessAppealMapper;
    private final BusinessAppealRepo businessAppealRepo;
    private final ReportedBusinessRepo reportedBusinessRepo;

    public BusinessAppealServiceImpl(BusinessAppealMapper businessAppealMapper, BusinessAppealRepo businessAppealRepo, ReportedBusinessRepo reportedBusinessRepo) {
        this.businessAppealMapper = businessAppealMapper;
        this.businessAppealRepo = businessAppealRepo;
        this.reportedBusinessRepo = reportedBusinessRepo;
    }

    @Override
    public BusinessAppeal saveBusinessAppeal(BusinessAppealDto businessAppealDto) {
        try {
            // Find reported business by business ID
            ReportedBusiness reportedBusiness = reportedBusinessRepo.findByBusinessBusinessId(businessAppealDto.getBusinessId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reported Business not found for businessId: " + businessAppealDto.getBusinessId()));

            // Map the DTO to entity
            BusinessAppeal businessAppeal = businessAppealMapper.toBusinessAppeal(businessAppealDto);
            businessAppeal.setStatus("PENDING");

            // Set the reported business
            businessAppeal.setReportedBusiness(reportedBusiness);

            // Save and return the business appeal
            return businessAppealRepo.save(businessAppeal);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save business appeal: " + e.getMessage(), e);
        }
    }


    @Override
    public List<BusinessAppeal> getAllBusinessAppeal() {
        try{
            return businessAppealRepo.findBusinessAppealsByStatus("PENDING");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all business appeals: " + e.getMessage(), e);

        }
    }

    @Override
    public BusinessAppeal updateBusinessAppealStatus(Integer appealId, String status) {
        return null;
    }
}

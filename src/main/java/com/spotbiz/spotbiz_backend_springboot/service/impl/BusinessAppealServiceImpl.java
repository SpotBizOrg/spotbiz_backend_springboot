package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAppealDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAppealResponseDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessAppeal;
import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;
import com.spotbiz.spotbiz_backend_springboot.exception.ResourceNotFoundException;
import com.spotbiz.spotbiz_backend_springboot.mapper.BusinessAppealMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessAppealRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.ReportedBusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessAppealService;
import com.spotbiz.spotbiz_backend_springboot.service.MailService;
import com.spotbiz.spotbiz_backend_springboot.templates.MailTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessAppealServiceImpl implements BusinessAppealService {

    private final BusinessAppealMapper businessAppealMapper;
    private final BusinessAppealRepo businessAppealRepo;
    private final ReportedBusinessRepo reportedBusinessRepo;
    private final MailService mailService;

    private final BusinessRepo businessRepo;

    public BusinessAppealServiceImpl(BusinessAppealMapper businessAppealMapper, BusinessAppealRepo businessAppealRepo, ReportedBusinessRepo reportedBusinessRepo, MailService mailService, BusinessRepo businessRepo) {
        this.businessAppealMapper = businessAppealMapper;
        this.businessAppealRepo = businessAppealRepo;
        this.reportedBusinessRepo = reportedBusinessRepo;
        this.mailService = mailService;
        this.businessRepo = businessRepo;
    }

    @Override
    public BusinessAppealDto saveBusinessAppeal(BusinessAppealDto businessAppealDto) {
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
            BusinessAppeal appeal =  businessAppealRepo.save(businessAppeal);
            return businessAppealMapper.toBusinessAppealDto(appeal);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save business appeal: " + e.getMessage(), e);
        }
    }


    @Override
    public List<BusinessAppealResponseDto> getAllBusinessAppeal() {
        try{
            List<BusinessAppeal> appealList =  businessAppealRepo.findBusinessAppealsByStatus("PENDING");
            // Map to BusinessAppealDto using streams
            return appealList.stream()
                    .map(businessAppealMapper::toBusinessAppealResponseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all business appeals: " + e.getMessage(), e);

        }
    }

    @Override
    public BusinessAppealDto updateBusinessAppealStatus(Integer appealId, String status) {
        try {
            if (!status.equals("APPROVED") && !status.equals("REJECTED")) {
                throw new IllegalArgumentException("Invalid status: " + status);
            } else if (status.equals("APPROVED")) {
                BusinessAppeal businessAppeal = businessAppealRepo.findById(appealId)
                        .orElseThrow(() -> new ResourceNotFoundException("Business Appeal not found for appealId: " + appealId));

                businessAppeal.setStatus("APPROVED");
                businessAppeal.getReportedBusiness().setStatus("REMOVED");

                Business business = businessRepo.findById(businessAppeal.getReportedBusiness().getBusiness().getBusinessId())
                        .orElseThrow(() -> new ResourceNotFoundException("Business not found for businessId: " + businessAppeal.getReportedBusiness().getBusiness().getBusinessId()));
                business.setStatus("APPROVED");

                String mail = MailTemplate.appealApprovedTemplate(business.getUser().getName());
                mailService.sendHtmlMail(business.getUser().getEmail(), "Update: Your Appeal Request Has Been Considered", mail);

                BusinessAppeal businessAppeal1 = businessAppealRepo.save(businessAppeal);
                return businessAppealMapper.toBusinessAppealDto(businessAppeal1);
            } else {
                BusinessAppeal businessAppeal = businessAppealRepo.findById(appealId)
                        .orElseThrow(() -> new ResourceNotFoundException("Business Appeal not found for appealId: " + appealId));

                businessAppeal.setStatus("REJECTED");

                BusinessAppeal businessAppeal2 =  businessAppealRepo.save(businessAppeal);
                return businessAppealMapper.toBusinessAppealDto(businessAppeal2);
            }
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            throw new RuntimeException("Failed to update business appeal status: " + e.getMessage(), e);
        }
    }
}

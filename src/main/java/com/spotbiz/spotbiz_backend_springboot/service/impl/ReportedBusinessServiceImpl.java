package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.ReportedBusinessDto;
import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;
import com.spotbiz.spotbiz_backend_springboot.entity.Role;
import com.spotbiz.spotbiz_backend_springboot.mapper.ReportedBusinessMappr;
import com.spotbiz.spotbiz_backend_springboot.repo.ReportedBusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.ReportedBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportedBusinessServiceImpl implements ReportedBusinessService {

    private final ReportedBusinessMappr reportedBusinessMappr;
    private final ReportedBusinessRepo reportedBusinessRepo;

    @Autowired
    public ReportedBusinessServiceImpl(ReportedBusinessMappr reportedBusinessMappr, ReportedBusinessRepo reportedBusinessRepo) {
        this.reportedBusinessMappr = reportedBusinessMappr;
        this.reportedBusinessRepo = reportedBusinessRepo;
    }

    @Override
    public ReportedBusiness saveReportedBusiness(ReportedBusinessDto reportedBusinessDto) {
        try {
            ReportedBusiness reportedBusiness = reportedBusinessMappr.toReportedBusiness(reportedBusinessDto);
            reportedBusiness.getUser().setRole(Role.CUSTOMER);

            return reportedBusinessRepo.save(reportedBusiness);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save reported request: " + e.getMessage());
        }


//        return reportedBusiness;
    }
}

package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.ReportedBusinessDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;
import com.spotbiz.spotbiz_backend_springboot.entity.Role;
import com.spotbiz.spotbiz_backend_springboot.mapper.ReportedBusinessMappr;
import com.spotbiz.spotbiz_backend_springboot.repo.ReportedBusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessService;
import com.spotbiz.spotbiz_backend_springboot.service.MailService;
import com.spotbiz.spotbiz_backend_springboot.service.ReportedBusinessService;
import com.spotbiz.spotbiz_backend_springboot.templates.MailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportedBusinessServiceImpl implements ReportedBusinessService {

    private final ReportedBusinessMappr reportedBusinessMappr;
    private final ReportedBusinessRepo reportedBusinessRepo;
    private final BusinessService businessService;

    private final MailService mailService;

    @Autowired
    public ReportedBusinessServiceImpl(ReportedBusinessMappr reportedBusinessMappr, ReportedBusinessRepo reportedBusinessRepo, BusinessService businessService, MailService mailService) {
        this.reportedBusinessMappr = reportedBusinessMappr;
        this.reportedBusinessRepo = reportedBusinessRepo;
        this.businessService = businessService;
        this.mailService = mailService;
    }

    @Override
    public ReportedBusiness saveReportedBusiness(ReportedBusinessDto reportedBusinessDto) {
        try {
            ReportedBusiness reportedBusiness = reportedBusinessMappr.toReportedBusiness(reportedBusinessDto);
            reportedBusiness.getUser().setRole(Role.CUSTOMER);
            reportedBusiness.setStatus("NO ACTION");

            return reportedBusinessRepo.save(reportedBusiness);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save reported request: " + e.getMessage());
        }

    }

    @Override
    public List<ReportedBusiness> getAllReportedBusiness() {
        try {
            return reportedBusinessRepo.findReportedBusinessesByStatus("NO ACTION");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get reported businesses: " + e.getMessage());
        }
    }

    @Override
    public ReportedBusiness BanReportedBusiness(Integer reportId) {
        try {
            ReportedBusiness reportedBusiness = reportedBusinessRepo.findById(reportId).orElseThrow(() -> new RuntimeException("Reported business not found"));
            reportedBusiness.setStatus("BANNED");

            ReportedBusiness updatedReportRequest =  reportedBusinessRepo.save(reportedBusiness);
            if (updatedReportRequest.getStatus().equals("BANNED")) {

                Business updatedBusiness = businessService.updateBusinessStatus(reportedBusiness.getBusiness().getBusinessId(), "BANNED");

                if (updatedBusiness.getStatus().equals("BANNED")) {
                    String mail = MailTemplate.banBusinessTemplate(updatedBusiness.getUser().getName(), updatedReportRequest.getReason(), updatedBusiness.getBusinessId());
                    mailService.sendHtmlMail(updatedBusiness.getUser().getEmail(), "Important: Your Account Has Been Banned", mail);
                    return updatedReportRequest;
                } else {
                    throw new RuntimeException("Failed to ban reported business");
                }
            } else {
                throw new RuntimeException("Failed to ban reported business");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to ban reported business: " + e.getMessage());
        }
    }

    @Override
    public ReportedBusiness RemoveReportRequest(Integer reportId) {
        try {
            ReportedBusiness reportedBusiness = reportedBusinessRepo.findById(reportId).orElseThrow(() -> new RuntimeException("Reported business not found"));
            reportedBusiness.setStatus("REMOVED");

            return reportedBusinessRepo.save(reportedBusiness);
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove report request: " + e.getMessage());
        }
    }
}

package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.ReportedBusinessDto;
import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;

import java.util.List;

public interface ReportedBusinessService {

    ReportedBusiness saveReportedBusiness(ReportedBusinessDto reportedBusinessDto);

    List<ReportedBusiness> getAllReportedBusiness();

    ReportedBusiness BanReportedBusiness(Integer reportId);

    ReportedBusiness RemoveReportRequest(Integer reportId);
}

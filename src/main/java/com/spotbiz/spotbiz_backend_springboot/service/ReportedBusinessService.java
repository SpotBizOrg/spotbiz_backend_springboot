package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.ReportedBusinessDto;
import com.spotbiz.spotbiz_backend_springboot.dto.ReportedBusinessResponseDto;
import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;

import java.util.List;

public interface ReportedBusinessService {

    ReportedBusiness saveReportedBusiness(ReportedBusinessDto reportedBusinessDto);

    List<ReportedBusinessResponseDto> getAllReportedBusiness();

    ReportedBusinessDto BanReportedBusiness(Integer reportId);

    ReportedBusinessDto RemoveReportRequest(Integer reportId);
}

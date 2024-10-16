package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.ReportedBusinessDto;
import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;

public interface ReportedBusinessService {

    ReportedBusiness saveReportedBusiness(ReportedBusinessDto reportedBusinessDto);
}

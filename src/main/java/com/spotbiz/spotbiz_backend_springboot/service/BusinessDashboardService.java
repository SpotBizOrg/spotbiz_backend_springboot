package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDashboardDto;

public interface BusinessDashboardService {

    BusinessDashboardDto getBusinessDashboardData(Integer businessId);
}

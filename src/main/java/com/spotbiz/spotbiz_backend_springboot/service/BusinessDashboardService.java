package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDashboardDto;
import com.spotbiz.spotbiz_backend_springboot.entity.User;

public interface BusinessDashboardService {

    BusinessDashboardDto getBusinessDashboardData(User user);
}

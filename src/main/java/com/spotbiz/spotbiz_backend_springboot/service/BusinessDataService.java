package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDataDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;

public interface BusinessDataService {

    BusinessDataDto getBusinessData(Integer businessId);
}

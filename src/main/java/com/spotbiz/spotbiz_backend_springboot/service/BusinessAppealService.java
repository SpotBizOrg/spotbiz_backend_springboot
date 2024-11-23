package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAppealDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAppealResponseDto;

import java.util.List;

public interface BusinessAppealService {
    BusinessAppealDto saveBusinessAppeal(BusinessAppealDto businessAppealDto);

    List<BusinessAppealResponseDto> getAllBusinessAppeal();

    BusinessAppealDto updateBusinessAppealStatus(Integer appealId, String status);
}

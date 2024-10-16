package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAppealDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessAppeal;

import java.util.List;

public interface BusinessAppealService {
    BusinessAppeal saveBusinessAppeal(BusinessAppealDto businessAppealDto);

    List<BusinessAppeal> getAllBusinessAppeal();

    BusinessAppeal updateBusinessAppealStatus(Integer appealId, String status);
}

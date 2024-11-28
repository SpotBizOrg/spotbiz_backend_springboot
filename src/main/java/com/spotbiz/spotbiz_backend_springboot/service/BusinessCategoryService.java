package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessCategoryDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;

import java.util.Map;

public interface BusinessCategoryService {
    Business updateBusinessCategory(BusinessCategoryDto tags, String email);

    Map<String, Long> getBusinessCountByCategory();
}

package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessCategoryDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;

public interface BusinessCategoryService {
    Business updateBusinessCategory(BusinessCategoryDto tags, String email);
}

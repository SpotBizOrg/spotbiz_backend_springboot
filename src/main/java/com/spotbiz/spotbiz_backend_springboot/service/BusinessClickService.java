package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessClicksDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessClicks;

public interface BusinessClickService {

    BusinessClicksDto getBusinessClicks(Integer BusinessId);

    BusinessClicksDto handleBusinessClicks(BusinessClicksDto businessClicks);
}

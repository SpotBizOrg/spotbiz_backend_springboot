package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessBadgeDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessBadge;

import java.util.List;

public interface BusinessBadgeService {

    BusinessBadgeDto getNewBadge();

    List<BusinessBadgeDto> getPastBadges();
}

package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAccDetailsResponseDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessAccountDetails;

public interface BusinessAccountDetailsService {
    int insertBusinessAccountDetails(BusinessAccountDetails businessAccountDetails);
    BusinessAccDetailsResponseDto getBusinessAccountDetails(int id);
    BusinessAccDetailsResponseDto getBusinessAccountDetailsByReimburseId(int id);
}

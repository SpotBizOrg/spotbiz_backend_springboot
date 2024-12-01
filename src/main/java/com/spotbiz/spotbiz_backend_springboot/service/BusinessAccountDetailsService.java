package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.BusinessAccountDetails;

public interface BusinessAccountDetailsService {
    int insertBusinessAccountDetails(BusinessAccountDetails businessAccountDetails);
    BusinessAccountDetails getBusinessAccountDetails(int id);
    BusinessAccountDetails getBusinessAccountDetailsByReimburseId(int id);
}

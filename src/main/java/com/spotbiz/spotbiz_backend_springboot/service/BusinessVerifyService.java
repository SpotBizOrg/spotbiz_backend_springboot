package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;

import java.util.List;

public interface BusinessVerifyService {

    List<Business> getUnverifiedBusinesses();

    Business verfiyBusiness(Integer businessId);
}

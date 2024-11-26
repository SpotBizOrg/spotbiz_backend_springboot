package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.CustomerPicsDto;
import com.spotbiz.spotbiz_backend_springboot.entity.CustomerPics;

import java.util.List;

public interface CustomerPicsService {

    CustomerPics savePics(CustomerPicsDto customerPicsDto);

    List<CustomerPics> getAllCustomerPics();

    CustomerPics updatePic(CustomerPicsDto customerPicsDto);

    CustomerPics deletePic(int customerPicsId);
}

package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.CustomerChosenPicResponseDto;
import com.spotbiz.spotbiz_backend_springboot.dto.CustomerDto;

public interface CustomerChosenPicService {

    CustomerDto saveChosenPic(CustomerDto customerDto);

    CustomerChosenPicResponseDto getChosenPic(int userId);
}

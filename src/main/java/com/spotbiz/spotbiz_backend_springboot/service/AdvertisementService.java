package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;

public interface AdvertisementService {
    Advertisement saveAdvertisement(AdvertisementRequestDto advertisementRequestDto);

    String getKeywords(Integer id);
}

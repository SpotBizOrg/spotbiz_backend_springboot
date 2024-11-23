package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRequestDto;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRecommendationDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;

import java.util.List;

public interface AdvertisementService {
    Advertisement saveAdvertisement(AdvertisementRequestDto advertisementRequestDto);

    String getKeywords(Integer id);

    List<AdvertisementRecommendationDto> getAdvertisementRecommeondation(String tags);

    void updateStatusIfExpired(Advertisement ad);

    List<Advertisement> filterAdvertisementsByDate(List<Advertisement> ads, int daysAgo);

}

package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRequestDto;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRecommendationDto;
import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionBillingDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;

import java.util.List;
import java.util.Set;

public interface AdvertisementService {
    Advertisement saveAdvertisement(AdvertisementRequestDto advertisementRequestDto);

    String getKeywords(Integer id);

    Set<AdvertisementRecommendationDto> getAdvertisementRecommeondation(String tags, Set<AdvertisementRecommendationDto> list);

    void updateStatusIfExpired(Advertisement ad);

    List<Advertisement> filterAdvertisementsByDate(List<Advertisement> ads, int daysAgo);

    Boolean checkAdvertisementLimit(String email);
}

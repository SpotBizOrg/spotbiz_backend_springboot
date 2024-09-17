package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRecommendationDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import com.spotbiz.spotbiz_backend_springboot.entity.SearchHistory;

import java.util.List;

public interface ReccomondationService {

    SearchHistory saveReccomondation(SearchHistory newSearch);

    String [] getReccomondation(Integer userId);
}

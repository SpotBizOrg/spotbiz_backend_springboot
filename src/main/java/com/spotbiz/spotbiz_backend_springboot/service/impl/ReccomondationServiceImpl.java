package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRecommendationDto;
import com.spotbiz.spotbiz_backend_springboot.entity.SearchHistory;
import com.spotbiz.spotbiz_backend_springboot.repo.SearchHistoryRepo;
import com.spotbiz.spotbiz_backend_springboot.service.ReccomondationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReccomondationServiceImpl implements ReccomondationService {

    private final SearchHistoryRepo searchHistoryRepo;


    @Autowired
    public ReccomondationServiceImpl(SearchHistoryRepo searchHistoryRepo) {
        this.searchHistoryRepo = searchHistoryRepo;
    }

    @Override
    public SearchHistory saveReccomondation(SearchHistory newSearch) {
        try {
            newSearch = searchHistoryRepo.save(newSearch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return newSearch;
    }

    @Override
    public String [] getReccomondation(Integer userId) {

        try {
            String [] list = searchHistoryRepo.findGeneratedKeywordsByUserIdOrderByUpdatedAtDesc(userId);
            if (list != null) {
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public List<AdvertisementRecommendationDto> getAdvertisementRecommeondation(Integer userId, List<String> tags) {
//        String jsonString = createJsonString(tags);
//
//
//    }
//
//    public String createJsonString(List<String> keywords) {
//        try {
//            // Create a map to hold the key-value pair
//            Map<String, List<String>> keywordMap = new HashMap<>();
//            keywordMap.put("keywords", keywords);
//
//            // Use ObjectMapper to convert the map into a JSON string
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(keywordMap);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}

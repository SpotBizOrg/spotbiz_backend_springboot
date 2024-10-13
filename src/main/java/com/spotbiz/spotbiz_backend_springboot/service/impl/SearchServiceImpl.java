package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessBoxDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.SearchHistory;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.ReviewRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.SearchHistoryRepo;
import com.spotbiz.spotbiz_backend_springboot.service.ReviewService;
import com.spotbiz.spotbiz_backend_springboot.service.SearchService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final BusinessRepo businessRepo;

    private final SearchHistoryRepo searchHistoryRepo;

    private final ReviewRepo reviewRepo;

    private final ReviewServiceImpl reviewServiceImpl;

    @Autowired
    public SearchServiceImpl(BusinessRepo businessRepo, SearchHistoryRepo searchHistoryRepo, ReviewRepo reviewRepo, ReviewServiceImpl reviewServiceImpl) {
        this.businessRepo = businessRepo;
        this.searchHistoryRepo = searchHistoryRepo;
        this.reviewRepo = reviewRepo;
        this.reviewServiceImpl = reviewServiceImpl;
    }

//    private static final String SEARCH_API_URL = "https://b76b32e1-e608-48ec-9d90-6c4f3b188f37.mock.pstmn.io/search/";
    private static final String SEARCH_API_URL = "http://localhost:8000/search";


    @Override
    public List<String> getKeywords(String searchText) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create the GET request and append the searchText as a query parameter
            String url = SEARCH_API_URL + "?keyword=" + URLEncoder.encode(searchText, "UTF-8");
            System.out.println(url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                // Convert the response body to JSON format
                JSONObject jsonResponse = new JSONObject(responseBody);
                System.out.println(jsonResponse);

                SearchHistory newSearch = new SearchHistory(16, jsonResponse.toString());
                ReccomondationServiceImpl searchHistory = new ReccomondationServiceImpl(searchHistoryRepo);
                SearchHistory history = searchHistory.saveReccomondation(newSearch);

                if (history == null) {
                    throw new RuntimeException("Failed to save search history");
                }

                // Extract the array from the JSON response
                JSONArray jsonArray = jsonResponse.getJSONArray("keywords");

                System.out.println(jsonArray);

                // Convert the JSONArray to a List<String>
                List<String> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.getString(i));
                }

                return list;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve keywords: " + e.getMessage());
        }
    }

    @Override
    public Page<BusinessBoxDto> searchBusinesses(String[] keywords, Pageable pageable) {
        try {
            Page<Business> list = businessRepo.findByAnyTag(keywords, pageable);
            Page<BusinessBoxDto> newList = list.map(this::convertToBusinessBoxDto);
            return newList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to do search businesses: " + e.getMessage());

        }
    }


    private BusinessBoxDto convertToBusinessBoxDto(Business business) {
        BusinessBoxDto dto = new BusinessBoxDto();
        dto.setBusinessId(business.getBusinessId());
        dto.setName(business.getName());
        dto.setAddress(business.getAddress());
        dto.setLogo(business.getLogo());
        dto.setDescription(business.getDescription());
        dto.setStatus("open now");
        dto.setAvgRating(getAvgRatings(business.getBusinessId()));
        dto.setCategoryId(getBusinessCategory(business.getBusinessId()));


        return dto;
    }

    private Double getAvgRatings(Integer businessId) {
        try {
//            ReviewServiceImpl reviewService = new ReviewServiceImpl();
            return reviewServiceImpl.getAverageRating(businessId);
//            return ratings.stream().mapToInt(Integer::intValue).sum() / ratings.size();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get average ratings: " + e.getMessage());
        }
    }

    private int getBusinessCategory(Integer businessId) {
        try {
            return businessRepo.getBusinessCategory(businessId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get category name: " + e.getMessage());
        }
    }
}

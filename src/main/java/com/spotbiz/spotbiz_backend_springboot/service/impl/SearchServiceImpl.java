package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.SearchService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final BusinessRepo businessRepo;

    @Autowired
    public SearchServiceImpl(BusinessRepo businessRepo) {
        this.businessRepo = businessRepo;
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
    public List<Business> searchBusinesses(List<String> keywords, int page, int size) {
        try {
            List<Business> list = businessRepo.findByAnyTag(keywords);
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Failed to do search businesses: " + e.getMessage());

        }
    }
}

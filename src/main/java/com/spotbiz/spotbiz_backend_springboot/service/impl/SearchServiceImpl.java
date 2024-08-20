package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.service.SearchService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchServiceImpl implements SearchService {

    private static final String SEARCH_API_URL = "https://b76b32e1-e608-48ec-9d90-6c4f3b188f37.mock.pstmn.io/search/";

    @Override
    public List<String> getKeywords(String searchText) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create the GET request and append the searchText as a query parameter
            String url = SEARCH_API_URL + "?query=" + URLEncoder.encode(searchText, "UTF-8");
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                // Convert the response body to JSON format
                JSONObject jsonResponse = new JSONObject(responseBody);

                // Extract the array from the JSON response
                JSONArray jsonArray = jsonResponse.getJSONArray("message");

                // Convert the JSONArray to a List<String>
                List<String> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.getString(i));
                }

                return list;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

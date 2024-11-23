package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRecommendationDto;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import com.spotbiz.spotbiz_backend_springboot.entity.AdvertisementKeyword;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.exception.AdvertisementException;
import com.spotbiz.spotbiz_backend_springboot.mapper.AdvertisementMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.AdvertisementKeywordRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.AdvertisementRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.AdvertisementService;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementRepo advertisementRepository;

    @Autowired
    private BusinessRepo businessRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdvertisementKeywordRepo advertiesmentKeywordRepo;

    @Autowired
    private AdvertisementMapper advertisementMapper;



    @Override
    public Advertisement saveAdvertisement(AdvertisementRequestDto advertisementRequestDto) {
        Advertisement advertisement = new Advertisement();
        try {
            String jsonData = convertToJson(advertisementRequestDto);
            advertisement.setData(jsonData);
        } catch (Exception e) {
            throw new AdvertisementException.JsonConversionException("Error converting data to JSON", e);
        }

        advertisement.setStatus(true);

        Optional<Business> optionalBusiness = businessRepository.findById(advertisementRequestDto.getBusinessId());
        System.out.println(advertisementRequestDto);
        if (optionalBusiness.isEmpty()) {
            System.out.println(optionalBusiness.get().getBusinessId());
            throw new AdvertisementException.BusinessNotFoundException("Business not found");
        }

        advertisement.setBusiness(optionalBusiness.get());

        Map<String, List<String>> jsonMap = new HashMap<>();
        jsonMap.put("keywords", advertisementRequestDto.getTags());

        ObjectMapper objectMapper = new ObjectMapper();


        try {
            Advertisement result = advertisementRepository.save(advertisement);
            try {
                String jsonString = objectMapper.writeValueAsString(jsonMap);
                AdvertisementKeyword keys = new AdvertisementKeyword();
                keys.setAdvertisement(result);
                keys.setTags(jsonString);
                advertiesmentKeywordRepo.save(keys);

                System.out.println(jsonString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        } catch (Exception e) {
            throw new AdvertisementException.AdvertisementSaveException("Error saving advertisement", e);
        }

    }

    @Override
    public String getKeywords(Integer id) {
        try {
           AdvertisementKeyword temp =  advertiesmentKeywordRepo.findByAdvertisementId(id);
           return temp.getTags();
        }
        catch (Exception e){
            throw new RuntimeException("AdvertisementKeyword not found", e);
        }

    }

    @Override
    public List<AdvertisementRecommendationDto> getAdvertisementRecommeondation(String tags) {

        String [] keywords = extractKeywords(tags);

        List<Object[]> recommendations = advertisementRepository.findByKeywords(keywords);
        if (recommendations.isEmpty()) {
            return null;
        }

        List<AdvertisementRecommendationDto> dtos = new ArrayList<>();
        for (Object[] row : recommendations) {
            AdvertisementRecommendationDto dto = getAdvertisementRecommendationDto(row);
            dtos.add(dto);
        }



        return dtos;

    }

    private static AdvertisementRecommendationDto getAdvertisementRecommendationDto(Object[] row) {
        Integer adsId = (Integer) row[0];              // Assuming the first column is ads_id
        Boolean status = (Boolean) row[1];         // Assuming the second column is status
        String data = (String) row[2];           // Assuming the third column is data
        Integer businessId = (Integer) row[3];         // Assuming the fourth column is business_id
        String tagss = (String) row[4];           // Assuming the fifth column is tags (in JSON format)

        AdvertisementRecommendationDto dto = new AdvertisementRecommendationDto();
        dto.setAdsId(adsId);
        dto.setStatus(status);
        dto.setData(data);
        dto.setBusinessId(businessId);
        dto.setTags(tagss);
        return dto;
    }

    public void saveImage(String base64Image, String filePath) throws IOException {
        String[] parts = base64Image.split(",");
        String imageString = parts[1];

        byte[] imageBytes = Base64.getDecoder().decode(imageString);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
        }
    }

    private String convertToJson(AdvertisementRequestDto dto) {
        Map<String, Object> dataMap = new HashMap<>();

        // Extracting the image type from the base64 string
        String base64Image = dto.getImg();
        String imageType = base64Image.substring(base64Image.indexOf("/") + 1, base64Image.indexOf(";"));
        String uniqueFileName = "ad_" + UUID.randomUUID().toString() + "." + imageType;

        String filePath = "../spotbiz_frontend/src/assets/uploaded_ads/" + uniqueFileName;
        String simplifiedPath = "/src/assets/uploaded_ads/" + uniqueFileName;

        try {
            saveImage(base64Image, filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error saving image to file", e);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        String endDate = now.plusDays(7).format(formatter);

        dataMap.put("img", simplifiedPath);
        dataMap.put("startDate", formattedNow);
        dataMap.put("endDate", endDate);
        dataMap.put("description", dto.getDescription());

        try {
            return objectMapper.writeValueAsString(dataMap);
        } catch (Exception e) {
            throw new RuntimeException("Error converting data to JSON", e);
        }
    }


    public String[] extractKeywords(String jsonString) {
        try {
            // Initialize ObjectMapper to parse the JSON string
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON string into a JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Extract the array of keywords from the JsonNode
            JsonNode keywordsNode = jsonNode.get("keywords");

            // Convert the JsonNode to a String array
            String[] keywordsArray = objectMapper.convertValue(keywordsNode, String[].class);

            return keywordsArray;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateStatusIfExpired(Advertisement ad) {
        try {
            JsonNode jsonData = objectMapper.readTree(ad.getData());
            String endDateString = jsonData.get("endDate").asText();

            LocalDateTime endDate = LocalDateTime.parse(endDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (endDate.isBefore(LocalDateTime.now()) && Boolean.TRUE.equals(ad.getStatus())) {
                ad.setStatus(false);
                advertisementRepository.save(ad);
            }
        } catch (Exception e) {
            System.err.println("Failed to parse advertisement data: " + e.getMessage());
        }
    }

    public List<Advertisement> filterAdvertisementsByDate(List<Advertisement> ads, int daysAgo) {
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thresholdDate = now.minusDays(daysAgo);

        return ads.stream()
                .filter(ad -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(ad.getData());
                        String startDateStr = jsonNode.get("startDate").asText();
                        LocalDateTime startDate = LocalDateTime.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        return startDate.isAfter(thresholdDate) && startDate.isBefore(now);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }




}

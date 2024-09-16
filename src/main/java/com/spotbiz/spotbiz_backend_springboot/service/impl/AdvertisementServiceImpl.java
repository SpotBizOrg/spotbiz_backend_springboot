package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import com.spotbiz.spotbiz_backend_springboot.entity.AdvertisementKeyword;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.exception.AdvertisementException;
import com.spotbiz.spotbiz_backend_springboot.repo.AdvertisementKeywordRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.AdvertisementRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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



}

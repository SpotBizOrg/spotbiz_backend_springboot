package com.spotbiz.spotbiz_backend_springboot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessOwnerDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessCategory;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.spotbiz.spotbiz_backend_springboot.mapper.AdvertisementMapper;
import com.spotbiz.spotbiz_backend_springboot.mapper.BusinessMapper;
import com.spotbiz.spotbiz_backend_springboot.mapper.UserMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.AdvertisementRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessCategoryRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BusinessOwnerService {

    @Autowired
    private BusinessRepo businessRepo;
    @Autowired
    private BusinessCategoryRepo businessCategoryRepo;
    @Autowired
    private AdvertisementRepo advertisementRepo;
    @Autowired
    private AdvertisementMapper advertisementMapper;
    @Autowired
    private BusinessMapper businessMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepo userRepo;

    public BusinessOwnerDto getDetails(User user) {
        return userMapper.toBusinessOwnerDto(user);
    }

    public BusinessDto getBusinessDetails(User user) {
        BusinessDto dto = new BusinessDto();
        Integer userId = user.getUserId();

        Business business = businessRepo.findByUserUserId(userId);
        Optional<BusinessCategory> businessCategory = businessCategoryRepo.findByBusiness(business);

        BusinessDto businessDto = businessMapper.toBusinessDto(business);

        if(businessCategory.isPresent()) {
            businessDto.setCategoryId(businessCategory.get().getCategory().getCategoryId());
            List<String> tags = parseJsonString(businessCategory.get().getTags());
            businessDto.setTags(tags);

            return businessDto;
        }

        return businessDto;

    }

    public List<AdvertisementDto> getAdvertisements(User user){

        Integer userId = user.getUserId();
        Business business = businessRepo.findByUserUserId(userId);
        if (business != null) {
            List<Advertisement> ads = advertisementRepo.findByBusinessBusinessId(business.getBusinessId());
            return advertisementMapper.mapToAdvertisementDtos(ads);
        }

        return null;
    }

//    public List<AdvertisementDto> getAdvertisementsByTags(Integer userId, String tags){
//
//        Business business = businessRepo.findByUserUserId(userId);
//        if (business != null) {
//            List<Advertisement> ads = advertisementRepo.findByBusinessBusinessIdAndTagsContaining(business.getBusinessId(), tags);
//            return advertisementMapper.mapToAdvertisementDtos(ads);
//        }
//
//        return null;
//    }

    public ResponseEntity<?> updateOwner(User user, BusinessOwnerDto dto) {
        try {
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPhoneNo(dto.getPhoneNo());
            userRepo.save(user);
            return ResponseEntity.ok(user);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }

    }

    public List<String> parseJsonString(String jsonString) {
        try {
            // Use ObjectMapper to parse the JSON string into a map
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<String>> keywordMap = objectMapper.readValue(jsonString, new TypeReference<Map<String, List<String>>>(){});

            // Extract and return the list of keywords
            return keywordMap.get("keywords");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


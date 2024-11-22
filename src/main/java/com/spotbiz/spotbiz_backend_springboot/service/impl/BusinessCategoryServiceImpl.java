package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessCategoryDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessCategory;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessCategoryRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BusinessCategoryServiceImpl implements BusinessCategoryService {

    @Autowired
    BusinessRepo businessRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    BusinessCategoryRepo businessCategoryRepo;

    @Override
    public Business updateBusinessCategory(BusinessCategoryDto dto, String email) {
        try {
            Optional<User> user = userRepo.findByEmail(email);
            if (user.isPresent()) {
                Business existingBusiness = businessRepo.findByUserUserId(user.get().getUserId());
                if (existingBusiness != null) {
                    Optional<BusinessCategory> businessCategory = businessCategoryRepo.findByBusinessId(existingBusiness.getBusinessId());
                    if (businessCategory.isPresent()) {
                        BusinessCategory category = businessCategory.get();
                        String tagsJson = convertToJson(dto.getTags());
                        category.setTags(tagsJson);
                        businessCategoryRepo.save(category);
                    } else {
                        throw new NoSuchElementException("Business category not found");
                    }
                    return existingBusiness;
                } else {
                    throw new RuntimeException("Business not found for the given user");
                }
            } else {
                throw new RuntimeException("User not found with the provided email");
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't update business category", e);
        }
    }

    @Override
    public Map<String, Long> getBusinessCountByCategory() {
        try {
            List<Object[]> results = businessCategoryRepo.countBusinessesByCategory();
            Map<String, Long> categoryBusinessCount = new HashMap<>();

            for (Object[] result : results) {
                String categoryName = (String) result[0];
                Long businessCount = (Long) result[1];
                categoryBusinessCount.put(categoryName, businessCount);
            }

            return categoryBusinessCount;
        }catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching category data", e);
        }
    }

    private String convertToJson(List<String> tags) {
        try {
            Map<String, List<String>> tagsMap = new HashMap<>();
            tagsMap.put("keywords", tags);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(tagsMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting tags to JSON", e);
        }
    }



}

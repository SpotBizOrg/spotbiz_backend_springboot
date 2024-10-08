package com.spotbiz.spotbiz_backend_springboot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessCategory;
import com.spotbiz.spotbiz_backend_springboot.entity.Category;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.mapper.BusinessMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessCategoryRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.CategoryRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class BusinessService {
    private final UserRepo userRepo;
    private final BusinessRepo businessRepo;


    @Autowired
    private BusinessMapper businessMapper;
    @Autowired
    private BusinessCategoryRepo businessCategoryRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    public BusinessService(UserRepo userRepo, BusinessRepo businessRepo) {
        this.userRepo = userRepo;
        this.businessRepo = businessRepo;
    }
    public Business addBusiness(BusinessDto businessDto, String email) {
        Optional<User> userOptional = userRepo.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Business owner does not exist");
        } else {
            try {
                Optional<Business> businessOptional = Optional.ofNullable(businessRepo.findByUserUserId(userOptional.get().getUserId()));

                if (businessOptional.isPresent()) {
                    throw new RuntimeException("Business already exists");
                }

                Business business = businessMapper.toBusiness(businessDto);
                business.setUser(userOptional.get());

                Integer categoryId = businessDto.getCategoryId();
                Optional<Category> categoryOptional = categoryRepo.findById(categoryId);

                if (categoryOptional.isPresent()) {
//                    List<String> businessCategory = categoryOptional.get().getTags();
                    List<String> tags  = businessDto.getTags();
                    String tagsString = createJsonString(tags);
                    BusinessCategory businessCategory = new BusinessCategory(categoryOptional.orElse(null), tagsString, business);

                    businessRepo.save(business);
                    businessCategoryRepo.save(businessCategory);

                }

                return business;
            } catch (Exception e) {
                throw new RuntimeException("Error saving business: " + e.getMessage());
            }
        }
    }

//    public Business updateBusiness(BusinessDto updatedBusinessDto, String email) {
//        Optional<User> userOptional = userRepo.findByEmail(email);
//
//
//        if (userOptional.isEmpty()) {
//            throw new RuntimeException("Business owner does not exist");
//        } else {
//            try {
//                Business existingBusiness = businessRepo.findByUserUserId(userOptional.get().getUserId());
//                System.out.println("existing business"+existingBusiness.getBusinessId());
//                if (existingBusiness.getBusinessId() == null) {
//                    throw new RuntimeException("Business does not exist");
//                }
//
//                businessMapper.updateBusinessFromDto(updatedBusinessDto, existingBusiness);
//                System.out.println("hi");
//                existingBusiness = businessRepo.save(existingBusiness);
//                businessCategoryRepo.save(new BusinessCategory(null, null, existingBusiness));
//
//                System.out.println("hi2");
//                Optional<BusinessCategory> existingCategoryOptional = businessCategoryRepo.findByBusiness(existingBusiness);
//
//                System.out.println("existing cat " + existingCategoryOptional.get().getTags());
//                if (existingCategoryOptional != null) {
//                    BusinessCategory existingCategory = existingCategoryOptional.get();
//                    Optional<Category> newCategoryOptional = categoryRepo.findById(updatedBusinessDto.getCategoryId());
//
//                    if (newCategoryOptional.isPresent()) {
//                        Category newCategory = newCategoryOptional.get();
//
//                        if (!existingCategory.getCategory().equals(newCategory)) {
//                            businessCategoryRepo.delete(existingCategory);
//                            BusinessCategory newBusinessCategory = new BusinessCategory();
//                            System.out.println("new category"+newCategory.getTags());
//                            newBusinessCategory.setBusiness(existingBusiness);
//                            newBusinessCategory.setCategory(newCategory);
//                            newBusinessCategory.setTags(updatedBusinessDto.getTags());
//                            businessCategoryRepo.save(newBusinessCategory);
//                        } else {
//                            existingCategory.setTags(updatedBusinessDto.getTags());
//                            businessCategoryRepo.save(existingCategory);
//                        }
//                    } else {
//                        throw new RuntimeException("Category does not exist");
//                    }
//                }
//            else {
//                    Optional<Category> newCategoryOptional = categoryRepo.findById(updatedBusinessDto.getCategoryId());
//                    System.out.println("new category"+newCategoryOptional.get().getTags());
//
//                    if (newCategoryOptional.isPresent()) {
//                        Category newCategory = newCategoryOptional.get();
//
//                        BusinessCategory newBusinessCategory = new BusinessCategory();
//                        newBusinessCategory.setBusiness(existingBusiness);
//                        newBusinessCategory.setCategory(newCategory);
//                        String tg = updatedBusinessDto.getTags();
//                        System.out.println(tg);
//                        newBusinessCategory.setTags(updatedBusinessDto.getTags());
//                        businessCategoryRepo.save(newBusinessCategory);
//                    } else {
//                        throw new RuntimeException("Category does not exist");
//                    }
//                }
//
//                return existingBusiness;
//            } catch (Exception e) {
//                throw new RuntimeException("Error updating business1: " + e.getMessage());
//            }
//        }
//    }

    public Business updateBusiness(BusinessDto updatedBusinessDto, String email) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        System.out.println("user"+userOptional.get().getUserId());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Business owner does not exist");
        }

        try {
            // Find existing business associated with the user
            Business existingBusiness = businessRepo.findByUserUserId(userOptional.get().getUserId());

            if (existingBusiness == null || existingBusiness.getBusinessId() == null) {
                throw new RuntimeException("Business does not exist");
            }

            // Update business details from DTO
            businessMapper.updateBusinessFromDto(updatedBusinessDto, existingBusiness);
            existingBusiness = businessRepo.save(existingBusiness);

            // Retrieve existing BusinessCategory
            Optional<BusinessCategory> existingCategoryOptional = businessCategoryRepo.findByBusiness(existingBusiness);

            Category newCategory = categoryRepo.findById(updatedBusinessDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category does not exist"));

            if (existingCategoryOptional.isPresent()) {
                BusinessCategory existingCategory = existingCategoryOptional.get();

                // If category or tags have changed, update them
                if (existingCategory.getTags()==null || !existingCategory.getCategory().equals(newCategory) || !existingCategory.getTags().equals(updatedBusinessDto.getTags())) {
                    existingCategory.setCategory(newCategory);

                    List<String> tags  = updatedBusinessDto.getTags();
                    String tagsString = createJsonString(tags);
                    existingCategory.setTags(tagsString);
                    businessCategoryRepo.save(existingCategory);
                }
            } else {
                // Create new BusinessCategory if it doesn't exist
                BusinessCategory newBusinessCategory = new BusinessCategory();
                newBusinessCategory.setBusiness(existingBusiness);
                newBusinessCategory.setCategory(newCategory);
                List<String> tags  = updatedBusinessDto.getTags();
                String tagsString = createJsonString(tags);
                newBusinessCategory.setTags(tagsString);
                System.out.println("new category"+newBusinessCategory.getTags());
                businessCategoryRepo.save(newBusinessCategory);
            }

            return existingBusiness;
        } catch (Exception e) {
            throw new RuntimeException("Error updating business: " + e.getMessage());
        }
    }


    public String getTags(Integer categoryId) {
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return category.getTags();
        } else {
            throw new RuntimeException("Category does not exist");
        }
    }

    public String createJsonString(List<String> keywords) {
        try {
            // Create a map to hold the key-value pair
            Map<String, List<String>> keywordMap = new HashMap<>();
            keywordMap.put("keywords", keywords);

            // Use ObjectMapper to convert the map into a JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(keywordMap);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

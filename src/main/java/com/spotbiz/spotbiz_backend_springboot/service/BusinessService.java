package com.spotbiz.spotbiz_backend_springboot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAdminResponseDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDto;
import com.spotbiz.spotbiz_backend_springboot.entity.*;
import com.spotbiz.spotbiz_backend_springboot.mapper.BusinessMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessCategoryRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.CategoryRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;


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
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Business owner does not exist"));

        Business existingBusiness = businessRepo.findByUserUserId(user.getUserId());
        if (existingBusiness == null) {
            throw new RuntimeException("Business does not exist");
        }

        businessMapper.updateBusinessFromDto(updatedBusinessDto, existingBusiness);
        businessRepo.save(existingBusiness);

        updateBusinessCategory(existingBusiness, updatedBusinessDto);

        return existingBusiness;
    }

    private void updateBusinessCategory(Business existingBusiness, BusinessDto updatedBusinessDto) {
        Integer newCategoryId = updatedBusinessDto.getCategoryId();
        if (newCategoryId == null) {
            return;
        }

        BusinessCategory existingCategory = businessCategoryRepo.findByBusiness(existingBusiness).orElse(null);
        Category newCategory = categoryRepo.findById(newCategoryId)
                .orElseThrow(() -> new RuntimeException("Category does not exist"));

        if (existingCategory == null || !existingCategory.getCategory().equals(newCategory)) {
            if (existingCategory != null) {
                businessCategoryRepo.delete(existingCategory);
            }
            BusinessCategory newBusinessCategory = new BusinessCategory();
            newBusinessCategory.setBusiness(existingBusiness);
            newBusinessCategory.setCategory(newCategory);
            newBusinessCategory.setTags(createJsonString(updatedBusinessDto.getTags())); // changed here cuz cant save
//            newBusinessCategory.setTags(updatedBusinessDto.getTags().toString());

            businessCategoryRepo.save(newBusinessCategory);
        } else {
            existingCategory.setTags(createJsonString(updatedBusinessDto.getTags())); // changed here cuz cant save
//            existingCategory.setTags(updatedBusinessDto.getTags().toString());

            businessCategoryRepo.save(existingCategory);
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

//
//    public List<Business> getAllBusinesses() {
//        List<Business> business = businessRepo.findAll();
//        List<Business> newBusinesses = new ArrayList<>();
//
//        for (Business businesss : business) {
//            if (businesss.getName() != null) {
//                newBusinesses.add(businesss);
//            }
//        }
//        return newBusinesses;
//    }

    public List<BusinessAdminResponseDto> getAllBusinesses() {
        List<Business> business = businessRepo.findAll();
        List<BusinessAdminResponseDto> newBusinesses = new ArrayList<>();

        for (Business businesss : business) {
            if (businesss.getName() != null) {
                BusinessAdminResponseDto dto = businessMapper.toBusinessAdminDto(businesss);
                newBusinesses.add(dto);
            }
        }
        return newBusinesses;

    }

    public Business updateBusinessStatus(Integer businessId, String status){
        try {
            Business business = businessRepo.findById(businessId).orElseThrow(() -> new RuntimeException("Business not found"));
            business.setStatus(status);
            return businessRepo.save(business);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update business status: " + e.getMessage());
        }
    }

//    public Business updateBusinessSubscription(SubscriptionBilling subscriptionBilling){
//        try {
//            Business business = businessRepo.findById(subscriptionBilling.getBusiness().getBusinessId()).orElseThrow(() -> new RuntimeException("Business not found"));
//            business.setSubscriptionBilling(subscriptionBilling);
//            return businessRepo.save(business);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to update business subscription: " + e.getMessage());
//        }
//    }

}

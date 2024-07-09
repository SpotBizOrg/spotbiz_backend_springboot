package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessCategory;
import com.spotbiz.spotbiz_backend_springboot.entity.Category;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.mapper.BusinessMapper;

import com.spotbiz.spotbiz_backend_springboot.mapper.CategoryMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessCategoryRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.CategoryRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class BusinessService {
    private final UserRepo userRepo;
    private final BusinessRepo businessRepo;
    private final CategoryRepo CategoryRepo ;


    @Autowired
    private BusinessMapper businessMapper;
    @Autowired
    private BusinessCategoryRepo businessCategoryRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CategoryMapper categoryMapper;


    @Autowired
    public BusinessService(UserRepo userRepo, BusinessRepo businessRepo, CategoryRepo categoryRepo) {
        this.userRepo = userRepo;
        this.businessRepo = businessRepo;
        this.CategoryRepo = categoryRepo;
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
                    BusinessCategory businessCategory = new BusinessCategory(categoryOptional.orElse(null), businessDto.getTags(), business);

                    businessRepo.save(business);
                    businessCategoryRepo.save(businessCategory);

                }

                return business;
            } catch (Exception e) {
                throw new RuntimeException("Error saving business: " + e.getMessage());
            }
        }
    }



    public Business updateBusiness(BusinessDto updatedBusinessDto, String email) {
        Optional<User> userOptional = userRepo.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Business owner does not exist");
        } else {
            try {
                Business existingBusiness = businessRepo.findByUserUserId(userOptional.get().getUserId());
                if (existingBusiness.getBusinessId() == null) {
                    throw new RuntimeException("Business does not exist");
                }

                businessMapper.updateBusinessFromDto(updatedBusinessDto, existingBusiness);
                existingBusiness = businessRepo.save(existingBusiness);

                Optional<BusinessCategory> existingCategoryOptional = businessCategoryRepo.findByBusiness(existingBusiness);

                if (existingCategoryOptional.isPresent()) {
                    BusinessCategory existingCategory = existingCategoryOptional.get();
                    Optional<Category> newCategoryOptional = categoryRepo.findById(updatedBusinessDto.getCategoryId());

                    if (newCategoryOptional.isPresent()) {
                        Category newCategory = newCategoryOptional.get();

                        if (!existingCategory.getCategory().equals(newCategory)) {
                            // Delete the old BusinessCategory
                            businessCategoryRepo.delete(existingCategory);

                            // Create and save the new BusinessCategory
                            BusinessCategory newBusinessCategory = new BusinessCategory();
                            newBusinessCategory.setBusiness(existingBusiness);
                            newBusinessCategory.setCategory(newCategory);
                            newBusinessCategory.setTags(updatedBusinessDto.getTags());
                            businessCategoryRepo.save(newBusinessCategory);
                        } else {
                            existingCategory.setTags(updatedBusinessDto.getTags());
                            businessCategoryRepo.save(existingCategory);
                        }
                    } else {
                        throw new RuntimeException("Category does not exist");
                    }
                }

                return existingBusiness;
            } catch (Exception e) {
                throw new RuntimeException("Error updating business: " + e.getMessage());
            }
        }
    }


    public List<String> getTags(Integer category) {
        Optional<Category> categoryOptional =  categoryRepo.findById(category);
        if (categoryOptional.isPresent()) {
             String jsonArrayString = categoryOptional.get().getTags();
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            List<String> list = IntStream.range(0, jsonArray.length())
                    .mapToObj(jsonArray::getString)
                    .toList();
            return list;
        }
        throw new RuntimeException("Category does not exist");
    }
}

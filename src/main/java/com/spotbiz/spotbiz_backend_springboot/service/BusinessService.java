package com.spotbiz.spotbiz_backend_springboot.service;

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
            newBusinessCategory.setTags(updatedBusinessDto.getTags());
            businessCategoryRepo.save(newBusinessCategory);
        } else {
            existingCategory.setTags(updatedBusinessDto.getTags());
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
}

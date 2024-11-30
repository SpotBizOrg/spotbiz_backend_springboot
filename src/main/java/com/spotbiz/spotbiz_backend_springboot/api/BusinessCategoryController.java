package com.spotbiz.spotbiz_backend_springboot.api;


import com.spotbiz.spotbiz_backend_springboot.dto.BusinessCategoryDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Category;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessCategoryService;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class BusinessCategoryController {

    @Autowired
    private final BusinessOwnerService businessOwnerService;
    @Autowired
    private final BusinessCategoryService businessCategoryService;

    public BusinessCategoryController(BusinessOwnerService businessOwnerService, BusinessCategoryService businessCategoryService) {
        this.businessOwnerService = businessOwnerService;
        this.businessCategoryService = businessCategoryService;
    }

    @GetMapping("/name/{categoryId}")
    public ResponseEntity<?> getCategoryName(@PathVariable Integer categoryId) {
        Category categoryName = businessOwnerService.getCategoryName(categoryId);
        return ResponseEntity.ok(categoryName);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = businessOwnerService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/tag/update/{email}")
    public ResponseEntity<?> updateTag(@PathVariable String email, @RequestBody BusinessCategoryDto businessTags) {

        Business business = businessCategoryService.updateBusinessCategory(businessTags, email);
        return ResponseEntity.ok(business);

    }



}

//package com.spotbiz.spotbiz_backend_springboot.api;
//
//
//import com.spotbiz.spotbiz_backend_springboot.dto.ReviewRequestDto;
//import com.spotbiz.spotbiz_backend_springboot.entity.Category;
//import com.spotbiz.spotbiz_backend_springboot.entity.Review;
//import com.spotbiz.spotbiz_backend_springboot.service.BusinessOwnerService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/category")
//public class CategoryController {
//
//    private final BusinessOwnerService businessOwnerService;
//
//    public CategoryController(BusinessOwnerService businessOwnerService) {
//        this.businessOwnerService = businessOwnerService;
//    }
//
//    @GetMapping("/name/{categoryId}")
//    public ResponseEntity<?> getCategoryName(@PathVariable Integer categoryId) {
//        Category categoryName = businessOwnerService.getCategoryName(categoryId);
//        return ResponseEntity.ok(categoryName);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<Category>> getCategories() {
//        List<Category> categories = businessOwnerService.getAllCategories();
//        return ResponseEntity.ok(categories);
//    }
//
//
//
//}
package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.entity.Category;
import com.spotbiz.spotbiz_backend_springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/business_type")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    @PostMapping("/{categoryId}/tag")
//    public ResponseEntity<Map<String, String>> addTagToCategory(
//            @PathVariable Integer categoryId,
//            @RequestBody Map<String, String> request) {
//        String tagName = request.get("name");
//        System.out.println("Adding tag: " + tagName + " to category ID: " + categoryId);
//        try {
//            Category updatedCategory = categoryService.addTagToCategory(categoryId, tagName);
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "Tag added successfully");
//            response.put("tag", tagName);
//            return ResponseEntity.ok(response);
//        } catch (IOException e) {
//            System.err.println("IOException occurred: " + e.getMessage());
//            return ResponseEntity.status(500).body(Map.of("error", "Failed to add tag: " + e.getMessage()));
//        } catch (RuntimeException e) {
//            System.err.println("RuntimeException occurred: " + e.getMessage());
//            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
//        }
//    }


    @PostMapping("/{categoryId}/tag")
    public ResponseEntity<Map<String, String>> addTagToCategory(
            @PathVariable Integer categoryId,
            @RequestBody Map<String, String> request) {
        System.out.println("Received request payload: " + request);  // Debug line
        String tagName = request.get("name");
        System.out.println("Adding tag: " + tagName + " to category ID: " + categoryId);
        try {
            Category updatedCategory = categoryService.addTagToCategory(categoryId, tagName);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tag added successfully");
            response.put("tag", tagName);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("error", "Failed to add tag: " + e.getMessage()));
        } catch (RuntimeException e) {
            System.err.println("RuntimeException occurred: " + e.getMessage());
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }





    @GetMapping("/all")
    public ResponseEntity<?> getAllCategory(){
        try{
            List<Category> categoryList=categoryService.getAllCategories();
            return ResponseEntity.ok(categoryList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}

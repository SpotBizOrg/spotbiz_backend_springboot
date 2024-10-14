package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Category;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/business")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    @PostMapping("register/{email}")
    public ResponseEntity<?> addBusiness(@RequestBody BusinessDto business, @PathVariable String email) {
        try{
            Business verifiedBusiness = businessService.updateBusiness(business, email);
            return ResponseEntity.ok(verifiedBusiness);
        }
        catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("update/{email}")
    public ResponseEntity<?> updateBusiness(@RequestBody BusinessDto updatedBusiness, @PathVariable String email) {
        try {
            System.out.println(updatedBusiness.getTags());
            System.out.println(updatedBusiness.getUserId());
            System.out.println(updatedBusiness.getBusinessId());
            Business updatedDto = businessService.updateBusiness(updatedBusiness, email);
            return ResponseEntity.ok(updatedDto);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/tags/{category}")
    public ResponseEntity<?> getTagsByCategory(@PathVariable Integer category) {
        try {
            String tags =  businessService.getTags(category);
                return ResponseEntity.ok(tags);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve tags: " + ex.getMessage());
        }
    }

}

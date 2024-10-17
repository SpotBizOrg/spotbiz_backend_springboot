package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAppealDto;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessAppealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/business-appeal")
public class BusinessAppealController {

    @Autowired
    private BusinessAppealService businessAppealService;

    @PostMapping("/save")
    public ResponseEntity<?> saveBusinessAppeal(@RequestBody BusinessAppealDto businessAppealDto) {
        try{
            return ResponseEntity.ok(businessAppealService.saveBusinessAppeal(businessAppealDto));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to save business appeal: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBusinessAppeal() {
        try {
            return ResponseEntity.ok(businessAppealService.getAllBusinessAppeal());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to get all business appeals: " + e.getMessage());
        }
    }

    @PutMapping("/update/{appealId}")
    public ResponseEntity<?> updateBusinessAppealStatus(@PathVariable Integer appealId, @RequestParam String status) {
        try {
            return ResponseEntity.ok(businessAppealService.updateBusinessAppealStatus(appealId, status));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update business appeal status: " + e.getMessage());
        }
    }
}

package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDataDto;
import com.spotbiz.spotbiz_backend_springboot.dto.CustomErrorDto;
import com.spotbiz.spotbiz_backend_springboot.service.impl.BusinessDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/businessPage")
public class BusinessPageController {

    @Autowired
    public BusinessDataServiceImpl businessDataService;


    @GetMapping("/businessData")
    public ResponseEntity<?> getBusinessData(@RequestParam int businessId, @RequestParam int clientId) {

        try {
            BusinessDataDto business = businessDataService.getBusinessData(businessId, clientId);

            if (business == null) {
                return ResponseEntity.status(400).body(new CustomErrorDto("Business not found", "400"));
            }
            return ResponseEntity.ok(business);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to retrieve business data: " + e.getMessage());

        }
    }
}

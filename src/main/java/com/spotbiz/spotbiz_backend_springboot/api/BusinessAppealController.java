package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAppealDto;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessAppealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/business-appeal")
public class BusinessAppealController {

    @Autowired
    private BusinessAppealService businessAppealService;

    @PostMapping("/save")
    public ResponseEntity<?> saveBusinessAppeal(@RequestBody BusinessAppealDto businessAppealDto) {
        try{
            System.out.println("Business Appeal DTO: " + businessAppealDto.getBusinessId());
            return ResponseEntity.ok(businessAppealService.saveBusinessAppeal(businessAppealDto));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to save business appeal: " + e.getMessage());
        }
    }
}

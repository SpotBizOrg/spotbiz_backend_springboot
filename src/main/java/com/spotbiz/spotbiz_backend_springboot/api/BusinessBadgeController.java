package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessBadgeDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessBadge;
import com.spotbiz.spotbiz_backend_springboot.service.impl.BusinessBadgeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/business_badge")
public class BusinessBadgeController {

    private final BusinessBadgeServiceImpl businessBadgeService;

    public BusinessBadgeController(BusinessBadgeServiceImpl businessBadgeService) {
        this.businessBadgeService = businessBadgeService;
    }

    @GetMapping("/new")
    public ResponseEntity<?> getNewBadge(){

        try {
            BusinessBadgeDto businessBadgeDto = businessBadgeService.getNewBadge();
            return ResponseEntity.ok(businessBadgeDto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred");
        }
    }

    @GetMapping("/all/6")
    public ResponseEntity<?> getPastSixBadges(){
        try {
            List<BusinessBadgeDto> pastSixBadges = businessBadgeService.getPastBadges();
            return ResponseEntity.ok(pastSixBadges);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred");
        }
    }
}

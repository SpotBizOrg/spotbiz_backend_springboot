package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessBadgeDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessBadge;
import com.spotbiz.spotbiz_backend_springboot.service.impl.BusinessBadgeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.status(500).body("Error occurred while fetching the business badge data");
        }
    }

    @PostMapping
    public ResponseEntity<?> createNewBadge(@RequestBody BusinessBadgeDto businessBadgeDto){
        try {
            System.out.println(businessBadgeDto.getBusinessId());
            System.out.println(businessBadgeDto.getIssuedDate());
            BusinessBadgeDto newBusinessBadge = businessBadgeService.saveNewBadge(businessBadgeDto);
            return ResponseEntity.ok(newBusinessBadge);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while saving the business badge");
        }
    }
}

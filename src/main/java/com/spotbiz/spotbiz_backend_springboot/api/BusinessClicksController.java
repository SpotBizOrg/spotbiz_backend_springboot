package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessClicksDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessClicks;
import com.spotbiz.spotbiz_backend_springboot.service.impl.BusinessClicksServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/business-clicks")
public class BusinessClicksController {

    private final BusinessClicksServiceImpl businessClicksService;

    public BusinessClicksController(BusinessClicksServiceImpl businessClicksService) {
        this.businessClicksService = businessClicksService;
    }

    @PutMapping("/click")
    public ResponseEntity<?> insertClick(@RequestBody BusinessClicksDto businessClicksDto) {
        try{
            BusinessClicksDto newClick = businessClicksService.handleBusinessClicks(businessClicksDto);
            return ResponseEntity.ok(newClick);
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert click: " + e.getMessage());
        }
    }

    @GetMapping("/get/{businessId}")
    public ResponseEntity<?> getClicks(@PathVariable Integer businessId) {
        try {
            BusinessClicksDto businessClicks = businessClicksService.getBusinessClicks(businessId);
            return ResponseEntity.ok(businessClicks);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get clicks: " + e.getMessage());
        }
    }

}

package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDashboardDto;
import com.spotbiz.spotbiz_backend_springboot.service.impl.BusinessDashboardServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/business-dashboard")
public class BusinessDashboardController {

    private final BusinessDashboardServiceImpl businessDashboardService;

    public BusinessDashboardController(BusinessDashboardServiceImpl businessDashboardService) {
        this.businessDashboardService = businessDashboardService;
    }

    @GetMapping("/{businessId}")
    public ResponseEntity<?> getDashboardData(@PathVariable int businessId){
        try{
            System.out.println(businessId);
            BusinessDashboardDto dashboard = businessDashboardService.getBusinessDashboardData(businessId);
            return ResponseEntity.ok(dashboard);
        }catch (Exception e){
            return ResponseEntity.status(500).body("failed to retrieve dashboard data");
        }
    }
}

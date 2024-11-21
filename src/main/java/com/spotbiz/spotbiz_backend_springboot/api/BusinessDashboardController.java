package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDashboardDto;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import com.spotbiz.spotbiz_backend_springboot.service.impl.BusinessDashboardServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/business-dashboard")
public class BusinessDashboardController {

    private final UserService userService;
    private final BusinessDashboardServiceImpl businessDashboardService;

    public BusinessDashboardController(UserService userService, BusinessDashboardServiceImpl businessDashboardService) {
        this.userService = userService;
        this.businessDashboardService = businessDashboardService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getDashboardData(@PathVariable String email){
        try{
            User user = userService.getUserByEmail(email);
            if (user != null){
                BusinessDashboardDto dashboard = businessDashboardService.getBusinessDashboardData(user);
                return ResponseEntity.ok(dashboard);
            }
            return ResponseEntity.status(404).body("Invalid email");
//            BusinessDashboardDto dashboard = businessDashboardService.getBusinessDashboardData(businessId);

        }catch (Exception e){
            return ResponseEntity.status(500).body("failed to retrieve dashboard data");
        }
    }
}

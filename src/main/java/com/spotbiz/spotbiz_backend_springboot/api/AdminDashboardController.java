package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.AdminDashboardDataDto;
import com.spotbiz.spotbiz_backend_springboot.service.impl.AdminDashboardServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin-dashboard")
public class AdminDashboardController {

    private final AdminDashboardServiceImpl adminDashboardService;

    public AdminDashboardController(AdminDashboardServiceImpl adminDashboardService) {
        this.adminDashboardService = adminDashboardService;
    }


    @GetMapping
    public ResponseEntity<?> getDashboardData(){
        try {
            AdminDashboardDataDto adminDashboardDataDto = adminDashboardService.getDashboardData();
            return ResponseEntity.ok(adminDashboardDataDto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to retrieve dashboard data");
        }
    }
}

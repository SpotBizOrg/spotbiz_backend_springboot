package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.entity.BusinessType;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/business_type")
@CrossOrigin(origins = "http://localhost:8080") // Replace with your frontend URL
public class BusinessTypeController {
    @Autowired
    private BusinessTypeService businessTypeService;

    @GetMapping
    public List<BusinessType> getAllBusinessTypes() {
        return businessTypeService.getAllBusinessTypes();
    }
}

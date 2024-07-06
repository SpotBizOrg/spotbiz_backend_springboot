package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/business")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    @PostMapping("register/{email}")
    public ResponseEntity<?> addBusiness(@RequestBody BusinessDto business, @PathVariable String email) {
        try{
            Business verifiedBusiness = businessService.addBusiness(business, email);
            return ResponseEntity.ok(verifiedBusiness);
        }
        catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("update/{email}")
    public ResponseEntity<?> updateBusiness(@RequestBody BusinessDto updatedBusiness, @PathVariable String email) {
        try {
            Business updatedDto = businessService.updateBusiness(updatedBusiness, email);
            return ResponseEntity.ok(updatedDto);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}

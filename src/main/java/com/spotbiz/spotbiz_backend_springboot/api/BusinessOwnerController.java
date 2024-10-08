package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessOwnerDto;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessOwnerRegDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessOwnerService;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/business_owner")
public class BusinessOwnerController {

    private final UserService userService;
    private final BusinessOwnerService businessOwnerService;

    public BusinessOwnerController(UserService userService, BusinessOwnerService businessOwnerService) {
        this.userService = userService;
        this.businessOwnerService = businessOwnerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody BusinessOwnerRegDto dto) {
        try {
            User registeredUser = userService.registerBusinessOwner(dto);
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }

    }

    @GetMapping("/details/{email}")
    public ResponseEntity<?> getOwnerByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                BusinessOwnerDto owner = businessOwnerService.getDetails(user);
                return ResponseEntity.ok(owner);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user: " + ex.getMessage());
        }
    }

    @GetMapping("/business/{email}")
    public ResponseEntity<?> getBusinessByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                BusinessDto business = businessOwnerService.getBusinessDetails(user);
                return ResponseEntity.ok(business);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve business: " + ex.getMessage());
        }
    }

    @GetMapping("/advertisements/{email}")
    public ResponseEntity<?> getAdvertisementsByEmail(@PathVariable String email) {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                List<AdvertisementDto> ads = businessOwnerService.getAdvertisements(user);
                return ResponseEntity.ok(ads);
            } else {
                return ResponseEntity.notFound().build();
            }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<?> updateOwner(@RequestBody BusinessOwnerDto dto, @PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return businessOwnerService.updateOwner(user, dto);
        }
        return ResponseEntity.notFound().build();

    }
}

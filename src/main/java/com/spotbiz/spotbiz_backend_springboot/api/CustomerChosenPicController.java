package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.CustomerChosenPicResponseDto;
import com.spotbiz.spotbiz_backend_springboot.dto.CustomerDto;
import com.spotbiz.spotbiz_backend_springboot.service.impl.CustomerChosenPicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer/pics")
public class CustomerChosenPicController {

    @Autowired
    private CustomerChosenPicServiceImpl customerChosenPicService;

    @PostMapping()
    public ResponseEntity<?> saveChosenPic(@RequestBody CustomerDto customerDto){
        try {
            CustomerDto customerDto1 = customerChosenPicService.saveChosenPic(customerDto);
            return ResponseEntity.ok(customerDto1);
        }catch (Exception ex) {
            return ResponseEntity.status(500).body("Failed to save data: " + ex.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getChosenPic(@PathVariable int userId){
        try {
            CustomerChosenPicResponseDto customer = customerChosenPicService.getChosenPic(userId);
            return ResponseEntity.ok(customer);
        }catch (Exception ex) {
            return ResponseEntity.status(500).body("Failed to get data: " + ex.getMessage());
        }
    }
}

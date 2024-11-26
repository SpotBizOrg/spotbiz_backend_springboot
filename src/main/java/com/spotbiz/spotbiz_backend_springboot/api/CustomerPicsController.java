package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.CustomerPicsDto;
import com.spotbiz.spotbiz_backend_springboot.entity.CustomerPics;
import com.spotbiz.spotbiz_backend_springboot.service.impl.CustomerPicsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer_pic")
public class CustomerPicsController {

    @Autowired
    private CustomerPicsServiceImpl customerPicsService;

    @PostMapping
    public ResponseEntity<?> saveImages(@RequestBody CustomerPicsDto customerPicsDto){
        try{
//            System.out.println(customerPicsDto.getImageUrl());
            CustomerPics newCustomerPic = customerPicsService.savePics(customerPicsDto);
            return ResponseEntity.ok(newCustomerPic);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while saving data");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPics(){
        try {
            List<CustomerPics> customerPicsList = customerPicsService.getAllCustomerPics();
            return ResponseEntity.ok(customerPicsList);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while fetching data");
        }
    }
}

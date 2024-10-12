package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementDto;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import com.spotbiz.spotbiz_backend_springboot.entity.AdvertisementKeyword;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.repo.AdvertisementKeywordRepo;
import com.spotbiz.spotbiz_backend_springboot.service.AdvertisementService;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private UserService userService;



    @PostMapping("/add")
    public ResponseEntity<?> createAdvertisement(@RequestBody AdvertisementRequestDto advertisementRequestDto) {
        Advertisement savedAdvertisement = advertisementService.saveAdvertisement(advertisementRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdvertisement);

    }

    @GetMapping("/getTags/{id}")
    public ResponseEntity<?> getAdvertisementKeywords(@PathVariable Integer id) {
            String keys = advertisementService.getKeywords(id);
            return ResponseEntity.ok(keys);

    }



}

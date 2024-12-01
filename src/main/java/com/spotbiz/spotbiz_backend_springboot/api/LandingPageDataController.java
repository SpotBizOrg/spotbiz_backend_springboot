package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.service.impl.LandingPageServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/landingPageData")
public class LandingPageDataController {


    private final LandingPageServiceImpl landingPageService;

    public LandingPageDataController(LandingPageServiceImpl landingPageService) {
        this.landingPageService = landingPageService;
    }

    @GetMapping
    public ResponseEntity<?> getLandingPageData() {

        try {
            return ResponseEntity.ok(landingPageService.getLandingPageData());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while fetching the landing page data");
        }

    }
}

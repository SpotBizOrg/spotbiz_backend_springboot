package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRecommendationDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessOwnerService;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import com.spotbiz.spotbiz_backend_springboot.service.impl.AdvertisementServiceImpl;
import com.spotbiz.spotbiz_backend_springboot.service.impl.ReccomondationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendation")
public class RecommendationController {

    private final ReccomondationServiceImpl reccomondationService;
    private final UserService userService;
    private final AdvertisementServiceImpl advertisementService;

    public RecommendationController(ReccomondationServiceImpl reccomondationService, UserService userService, AdvertisementServiceImpl advertisementService) {
        this.reccomondationService = reccomondationService;
        this.userService = userService;
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public ResponseEntity<?> getRecommendations(@RequestParam Integer userId, @RequestParam String email) {
        try {

            // Fetch the user by email
            User user = userService.getUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found with email: " + email);
            }

            // Fetch the recommendation list
            String[] list = reccomondationService.getReccomondation(userId);

            // Check if the list is null
            if (list == null) {
                return ResponseEntity.status(404).body("No recommendations found for the user");
            }

            // Log and fetch the latest search
            String latestSearch = list[0];
            System.out.println("Latest search: " + latestSearch);


            // Fetch advertisement recommendations based on the latest search
            List<AdvertisementRecommendationDto> advertisementRecommendationDtoList = advertisementService.getAdvertisementRecommeondation(latestSearch);

            // Check if the recommendation list is null
            if (advertisementRecommendationDtoList == null || advertisementRecommendationDtoList.isEmpty()) {
                return ResponseEntity.status(404).body("No advertisements found for the search: " + latestSearch);
            }

            // Return the successful response with recommendations
            return ResponseEntity.ok(advertisementRecommendationDtoList);

        } catch (IllegalArgumentException ex) {
            // Handle invalid arguments
            return ResponseEntity.badRequest().body("Invalid input: " + ex.getMessage());
        } catch (Exception ex) {
            // Handle generic exceptions
            return ResponseEntity.status(500).body("An unexpected error occurred: " + ex.getMessage());
        }
    }
}

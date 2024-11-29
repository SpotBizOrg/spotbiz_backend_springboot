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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            System.out.println(list.length);

            // Check if the list is empty
            if (list == null || list.length == 0) {
                return ResponseEntity.status(404).body("No recommendations found for the user");
            }

            // Use a Set to ensure distinct AdvertisementRecommendationDto values
            Set<AdvertisementRecommendationDto> uniqueRecommendations = new HashSet<>();
            for (String tag : list) {
                advertisementService.getAdvertisementRecommeondation(tag, uniqueRecommendations);
            }

            // Convert Set back to List for response
            List<AdvertisementRecommendationDto> advertisementRecommendationDtoList = new ArrayList<>(uniqueRecommendations);

            // Check if the recommendation list is empty
            if (advertisementRecommendationDtoList.isEmpty()) {
                return ResponseEntity.status(404).body("No advertisements found for the given tags");
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

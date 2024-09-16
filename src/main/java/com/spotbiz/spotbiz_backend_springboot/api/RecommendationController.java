package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessOwnerService;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
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
    private final BusinessOwnerService businessOwnerService;
    private final UserService userService;

    public RecommendationController(ReccomondationServiceImpl reccomondationService, BusinessOwnerService businessOwnerService, UserService userService) {
        this.reccomondationService = reccomondationService;
        this.businessOwnerService = businessOwnerService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getRecommendations(@RequestParam Integer userId, @RequestParam String email) {

        List<String> list = reccomondationService.getReccomondation(userId);
        if (list == null) {
            return ResponseEntity.notFound().build();
        }
//        User user = userService.getUserByEmail(email);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }


        return ResponseEntity.ok(list);

//        return ResponseEntity.ok("Recommendations");
    }

}

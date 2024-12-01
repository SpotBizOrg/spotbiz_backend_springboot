package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscribeDto;
import com.spotbiz.spotbiz_backend_springboot.service.SubscribeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/sub_business")
public class SubscribeController {

    private final SubscribeService subscribeService;

    public SubscribeController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @GetMapping("/subscribed/{user_id}")
    public ResponseEntity<?> getSubscribedBusiness(@PathVariable int user_id) {
        try{
            List<SubscribeDto> subscribedBusinesses = subscribeService.getSubscribedBusinesses(user_id);
            if(subscribedBusinesses != null) {
                return ResponseEntity.ok().body(subscribedBusinesses);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscribed businesses are not found");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }

    @GetMapping("/subscribed/email/{user_id}")
    public ResponseEntity<?> getSubscribedBusinessWithEmail(@PathVariable int user_id) {
        try{
            List<SubscribeDto> subscribedBusinesses = subscribeService.getSubscribedBusinesses(user_id);
            if(subscribedBusinesses != null) {
                return ResponseEntity.ok().body(subscribedBusinesses);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscribed businesses are not found");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }

    @GetMapping("/subscribers/{business_id}")
    public ResponseEntity<?> getSubscribers(@PathVariable int business_id) {
        try{
            List<SubscribeDto> subscribers = subscribeService.getSubscribers(business_id);
            if(subscribers != null) {
                return ResponseEntity.ok().body(subscribers);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscribers are not found");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }

    @GetMapping("/subscribe_count/{business_id}")
    public ResponseEntity<?> getSubscribersCount(@PathVariable int business_id) {
        try{
            int subscribersCount = subscribeService.getSubscriberCount(business_id);
            if(subscribersCount > 0) {
                return ResponseEntity.ok().body(subscribersCount);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscribers are not found");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeBusiness(@RequestBody SubscribeDto subscribeDto) {
        try{
            SubscribeDto subscribedBusiness = subscribeService.subscribe(subscribeDto);
            if(subscribedBusiness != null) {
                return ResponseEntity.ok().body(subscribedBusiness);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Business is not found");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }

    @DeleteMapping("/unsubscribe/{user_id}/{business_id}")
    public ResponseEntity<?> unsubscribeBusiness(@PathVariable int user_id, @PathVariable int business_id) {
        try{
            int subscribersCount = subscribeService.unsubscribe(user_id, business_id);
            if(subscribersCount > 0) {
                return ResponseEntity.ok().body(subscribersCount);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Business is not found");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }

    @GetMapping("/check_subscription/{user_id}/{business_id}")
    public ResponseEntity<?> checkSubscription(@PathVariable int user_id, @PathVariable int business_id) {
        try{
            boolean isSubscribed = subscribeService.checkSubscription(user_id, business_id);
            return ResponseEntity.ok().body(isSubscribed);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }



}

package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionBillingDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessService;
import com.spotbiz.spotbiz_backend_springboot.service.impl.SubscriptionBillingServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription-billing")
public class SubscriptionBillingController {
    private final SubscriptionBillingServiceImpl subscriptionBillingService;
    private final BusinessService businessService;
    public SubscriptionBillingController(SubscriptionBillingServiceImpl subscriptionBillingService, BusinessService businessService) {
        this.subscriptionBillingService = subscriptionBillingService;
        this.businessService = businessService;
    }

    @PostMapping("/subscription-billing")
    public ResponseEntity<?> insertSubscriptionBilling(@RequestBody SubscriptionBillingDto subscriptionBillingDto) {
        try {
            SubscriptionBilling newSubscriptionBilling = subscriptionBillingService.insertSubscriptionBilling(subscriptionBillingDto);
//            Business updatedBusiness = businessService.updateBusinessSubscription(newSubscriptionBilling);
            return ResponseEntity.ok(newSubscriptionBilling);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while inserting subscription billing");
        }
    }

    @GetMapping("/subscription-billing")
    public ResponseEntity<?> getAllSubscriptionBillings() {
        List<SubscriptionBillingDto> subscriptionBillings = subscriptionBillingService.getAllSubscriptionBillings();
        return ResponseEntity.ok(subscriptionBillings);
    }

    @GetMapping("/{subscriptionBillingId}")
    public ResponseEntity<?> getSubscriptionBillingById(@PathVariable int subscriptionBillingId) {
        SubscriptionBillingDto subscriptionBilling = subscriptionBillingService.getSubscriptionBillingById(subscriptionBillingId);
        return ResponseEntity.ok(subscriptionBilling);
    }

    @PutMapping("/{subscriptionBillingId}")
    public ResponseEntity<SubscriptionBillingDto> updateSubscriptionBilling(@PathVariable int subscriptionBillingId, @RequestBody SubscriptionBillingDto subscriptionBillingDto) {
        SubscriptionBillingDto subscriptionBilling = subscriptionBillingService.updateSubscriptionBilling(subscriptionBillingId, subscriptionBillingDto);
        return ResponseEntity.ok(subscriptionBilling);
    }

    @PutMapping("/delete/{subscriptionBillingId}")
    public ResponseEntity<SubscriptionBillingDto> markDeleteSubscriptionBilling(@PathVariable int subscriptionBillingId) {
        SubscriptionBillingDto subscriptionBilling = subscriptionBillingService.markDeleteSubscriptionBilling(subscriptionBillingId);
        return ResponseEntity.ok(subscriptionBilling);
    }

    @DeleteMapping("/{subscriptionBillingId}")
    public ResponseEntity<Integer> deleteSubscriptionBilling(@PathVariable int subscriptionBillingId) {
        int subscriptionBilling = subscriptionBillingService.deleteSubscriptionBilling(subscriptionBillingId);
        return ResponseEntity.ok(subscriptionBilling);
    }
}

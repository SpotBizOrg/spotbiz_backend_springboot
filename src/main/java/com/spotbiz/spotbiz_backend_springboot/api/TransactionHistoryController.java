package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.service.impl.TransactionHistoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transaction-history")
public class TransactionHistoryController {

    private final TransactionHistoryServiceImpl transacrionHistoryService;

    public TransactionHistoryController(TransactionHistoryServiceImpl transacrionHistoryService) {
        this.transacrionHistoryService = transacrionHistoryService;
    }

    @GetMapping("/subscription-billing")
    public ResponseEntity<?> getSubscriptionBillingTransactions(){
        try {
            return ResponseEntity.ok(transacrionHistoryService.getPastMonthBillings());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping("/reimbursements")
    public ResponseEntity<?> getAllPaidReimbursements() {
        try {
            return ResponseEntity.ok(transacrionHistoryService.getAllPaidReimbursements());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
}

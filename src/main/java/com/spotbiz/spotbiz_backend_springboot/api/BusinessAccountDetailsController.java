package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAccountDetailsDto;
import com.spotbiz.spotbiz_backend_springboot.dto.ReimbursementDto;
import com.spotbiz.spotbiz_backend_springboot.entity.*;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessAccountDetailsService;
import com.spotbiz.spotbiz_backend_springboot.service.ReimbursementService;
import com.spotbiz.spotbiz_backend_springboot.service.ScannedCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/business_account_details")
public class BusinessAccountDetailsController {

    private final ReimbursementService reimbursementService;
    private final BusinessRepo businessRepo;
    private final BusinessAccountDetailsService businessAccountDetailsService;

    public BusinessAccountDetailsController(ReimbursementService reimbursementService, BusinessRepo businessRepo, ScannedCouponService scannedCouponService, BusinessAccountDetailsService businessAccountDetailsService) {
        this.reimbursementService = reimbursementService;
        this.businessRepo = businessRepo;
        this.businessAccountDetailsService = businessAccountDetailsService;
    }

    @PostMapping("/{businessId}")
    public ResponseEntity<?> insertBusinessAccountDetails(@RequestBody BusinessAccountDetailsDto businessAccountDetailsDto, @PathVariable int businessId) {
        try {
            Business business = businessRepo.getReferenceById(businessId);
            BusinessAccountDetails businessAccountDetails = new BusinessAccountDetails();
            businessAccountDetails.setBusiness(business);
            businessAccountDetails.setAccountNo(businessAccountDetailsDto.getAccountNo());
            businessAccountDetails.setBankName(businessAccountDetailsDto.getBankName());
            businessAccountDetails.setBranchName(businessAccountDetailsDto.getBranchName());
            businessAccountDetails.setAccountHolderName(businessAccountDetailsDto.getAccountHolderName());
            businessAccountDetails.setPhoneNo(business.getContactNo());
            businessAccountDetails.setEmail(business.getUser().getEmail());

            int insertionStatus = businessAccountDetailsService.insertBusinessAccountDetails(businessAccountDetails);

            if (insertionStatus > 0) {
                return ResponseEntity.ok(insertionStatus);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert details");
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBusinessAccountDetails(@PathVariable int id) {
        try {
            BusinessAccountDetails businessAccountDetails = businessAccountDetailsService.getBusinessAccountDetails(id);
            if(businessAccountDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No account details found");
            }
            return ResponseEntity.ok(businessAccountDetails);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}

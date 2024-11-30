package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.ReimbursementDto;
import com.spotbiz.spotbiz_backend_springboot.entity.ReimbursementStatus;
import com.spotbiz.spotbiz_backend_springboot.entity.Reimbursements;
import com.spotbiz.spotbiz_backend_springboot.entity.ScannedCouponStatus;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.ScannedCouponRepo;
import com.spotbiz.spotbiz_backend_springboot.service.ReimbursementService;
import com.spotbiz.spotbiz_backend_springboot.service.ScannedCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reimbursements")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;
    private final BusinessRepo businessRepo;
    private final ScannedCouponService scannedCouponService;

    public ReimbursementController(ReimbursementService reimbursementService, BusinessRepo businessRepo, ScannedCouponService scannedCouponService) {
        this.reimbursementService = reimbursementService;
        this.businessRepo = businessRepo;
        this.scannedCouponService = scannedCouponService;
    }

    @PostMapping
    public ResponseEntity<?> insertAlreadyPlayedGame(@RequestBody ReimbursementDto reimbursementDto) {
        try {
            Reimbursements reimbursement = new Reimbursements();
            reimbursement.setBusiness(businessRepo.getReferenceById(reimbursementDto.getBusinessId()));
            reimbursement.setDateTime(reimbursementDto.getDateTime());

            float amount = 0;

            for (int id : reimbursementDto.getScannedCouponIds()) {
                amount += scannedCouponService.findAmountById(id);
            }

            reimbursement.setAmount(amount);
            reimbursement.setStatus(ReimbursementStatus.PENDING);

            int insertionStatus = reimbursementService.insertReimbursement(reimbursement);

            if (insertionStatus > 0) {

                for (int id : reimbursementDto.getScannedCouponIds()) {
                    scannedCouponService.changeStatus(id, ScannedCouponStatus.REQUESTED);
                }

                return ResponseEntity.ok(insertionStatus);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert reimburse request");
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReimbursementsByBusinessId(@PathVariable int id) {
        try {
            List<Reimbursements> reimbursements = reimbursementService.getReimbursementByBusinessIdAndStatus(id);
            if(reimbursements.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reimbursements found");
            }
            return ResponseEntity.ok(reimbursements);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllReimbursements() {
        try {
            List<Reimbursements> reimbursements = reimbursementService.getAllReimbursementsByStatus();
            if(reimbursements.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reimbursements found");
            }
            return ResponseEntity.ok(reimbursements);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable int id, @PathVariable ReimbursementStatus status) {
        try {
            int updatedStatus = reimbursementService.changeStatus(id, status);
            if(updatedStatus <= 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Counld not update");
            }
            return ResponseEntity.ok(updatedStatus);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}

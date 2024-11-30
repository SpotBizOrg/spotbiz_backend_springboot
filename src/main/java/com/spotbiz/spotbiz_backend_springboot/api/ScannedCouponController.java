package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.entity.*;
import com.spotbiz.spotbiz_backend_springboot.service.ScannedCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/scanned_coupon")
public class ScannedCouponController {

    private final ScannedCouponService scannedCouponService;

    public ScannedCouponController(ScannedCouponService scannedCouponService) {
        this.scannedCouponService = scannedCouponService;
    }

    @PostMapping()
    public ResponseEntity<?> insertScannedCoupon(@RequestBody ScannedCoupon scannedCoupon) {
        int insertedScannedCoupon = scannedCouponService.insertScannedCoupon(scannedCoupon);
        if(insertedScannedCoupon > 0) {
            return ResponseEntity.ok().body(insertedScannedCoupon);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insert coupon failed");
    }

    @GetMapping("/business/{id}")
    public ResponseEntity<?> getScannedCoupon(@PathVariable int id) {
        List<ScannedCoupon> scannedCoupons = scannedCouponService.getScannedCouponsForBusiness(id);
        if(scannedCoupons != null) {
            return ResponseEntity.ok().body(scannedCoupons);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insert coupon failed");
    }
}

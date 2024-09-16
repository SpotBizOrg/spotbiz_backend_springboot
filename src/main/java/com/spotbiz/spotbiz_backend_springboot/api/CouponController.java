package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.CouponDto;
import com.spotbiz.spotbiz_backend_springboot.entity.CouponStatus;
import com.spotbiz.spotbiz_backend_springboot.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api/v1/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertCoupon(@RequestBody CouponDto couponDto) {
        int insertedCoupon = couponService.insertCoupon(couponDto);
        if(insertedCoupon > 0) {
            return ResponseEntity.ok().body(insertedCoupon);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insert coupon failed");
    }

    @GetMapping("/check/{coupon_id}")
    public ResponseEntity<?> checkCoupon(@PathVariable int coupon_id) {
        CouponStatus couponStatus = couponService.checkCoupon(coupon_id);
        if(couponStatus != null) {
            return ResponseEntity.ok().body(couponStatus);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check coupon failed");
    }

    @PutMapping("/issue/{user_id}/{coupon_id}")
    public ResponseEntity<?> issueCoupon(@PathVariable int user_id, @PathVariable int coupon_id) {
        int issueCoupon = couponService.issueCoupon(user_id, coupon_id);
        if(issueCoupon > 0) {
            return ResponseEntity.ok().body(issueCoupon);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Issue coupon failed");
    }

    @PutMapping("/use/{business_id}/{coupon_id}")
    public ResponseEntity<?> useCoupon(@PathVariable int business_id, @PathVariable int coupon_id) {
        int issueCoupon = couponService.useCoupon(business_id, coupon_id);
        if(issueCoupon > 0) {
            return ResponseEntity.ok().body(issueCoupon);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Use coupon failed");
    }

    @PutMapping("/update/{coupon_id}")
    public ResponseEntity<?> updateCoupon(@RequestBody CouponDto couponDto, @PathVariable int coupon_id) {
        int issueCoupon = couponService.updateCoupon(couponDto, coupon_id);
        if(issueCoupon > 0) {
            return ResponseEntity.ok().body(issueCoupon);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update coupon failed");
    }

    @DeleteMapping("/delete/{coupon_id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable int coupon_id) {
        int issueCoupon = couponService.deleteCoupon(coupon_id);
        if(issueCoupon > 0) {
            return ResponseEntity.ok().body(issueCoupon);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete coupon failed");
    }

}

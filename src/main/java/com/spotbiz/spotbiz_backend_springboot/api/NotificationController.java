package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.CouponDto;
import com.spotbiz.spotbiz_backend_springboot.dto.NotificationTokenDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Coupon;
import com.spotbiz.spotbiz_backend_springboot.entity.CouponStatus;
import com.spotbiz.spotbiz_backend_springboot.entity.Notification;
import com.spotbiz.spotbiz_backend_springboot.service.CouponService;
import com.spotbiz.spotbiz_backend_springboot.service.NotificationService;
import com.spotbiz.spotbiz_backend_springboot.service.NotificationTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notification")
public class NotificationController {

    private final NotificationTokenService notificationTokenService;
    private final NotificationService notificationService;

    public NotificationController(NotificationTokenService notificationTokenService, NotificationService notificationService) {
        this.notificationTokenService = notificationTokenService;
        this.notificationService = notificationService;
    }

    @PostMapping("/token")
    public ResponseEntity<?> insertToken(@RequestBody NotificationTokenDto notificationTokenDto) {
        NotificationTokenDto insertedNotificationToken = notificationTokenService.insertNotificationToken(notificationTokenDto);
        if(insertedNotificationToken != null) {
            return ResponseEntity.ok().body(insertedNotificationToken);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insert token failed");
    }

    @GetMapping("/all/{user_id}")
    public ResponseEntity<?> getAllNotification(@PathVariable int user_id) {
        List<Notification> notificationList = notificationService.getAllNotification(user_id);
        if(notificationList != null) {
            return ResponseEntity.ok().body(notificationList);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Getting notification failed");
    }


//
//    @GetMapping("/check/{coupon_id}")
//    public ResponseEntity<?> checkCoupon(@PathVariable int coupon_id) {
//        CouponStatus couponStatus = couponService.checkCoupon(coupon_id);
//        if(couponStatus != null) {
//            return ResponseEntity.ok().body(couponStatus);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check coupon failed");
//    }
//
//    @PutMapping("/issue/{user_id}/{coupon_id}")
//    public ResponseEntity<?> issueCoupon(@PathVariable int user_id, @PathVariable int coupon_id) {
//        int issueCoupon = couponService.issueCoupon(user_id, coupon_id);
//        if(issueCoupon > 0) {
//            return ResponseEntity.ok().body(issueCoupon);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Issue coupon failed");
//    }
//
//    @PutMapping("/use/{business_id}/{coupon_id}")
//    public ResponseEntity<?> useCoupon(@PathVariable int business_id, @PathVariable int coupon_id) {
//        int issueCoupon = couponService.useCoupon(business_id, coupon_id);
//        if(issueCoupon > 0) {
//            return ResponseEntity.ok().body(issueCoupon);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Use coupon failed");
//    }
//
//    @PutMapping("/update/{coupon_id}")
//    public ResponseEntity<?> updateCoupon(@RequestBody CouponDto couponDto, @PathVariable int coupon_id) {
//        int issueCoupon = couponService.updateCoupon(couponDto, coupon_id);
//        if(issueCoupon > 0) {
//            return ResponseEntity.ok().body(issueCoupon);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update coupon failed");
//    }
//
//    @DeleteMapping("/delete/{coupon_id}")
//    public ResponseEntity<?> deleteCoupon(@PathVariable int coupon_id) {
//        int issueCoupon = couponService.deleteCoupon(coupon_id);
//        if(issueCoupon > 0) {
//            return ResponseEntity.ok().body(issueCoupon);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete coupon failed");
//    }

}

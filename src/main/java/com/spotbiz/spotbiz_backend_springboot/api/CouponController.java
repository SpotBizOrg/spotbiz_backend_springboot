package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.CouponDto;
import com.spotbiz.spotbiz_backend_springboot.entity.*;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.CouponService;
import com.spotbiz.spotbiz_backend_springboot.service.EncodedCouponService;
import com.spotbiz.spotbiz_backend_springboot.service.MailService;
import com.spotbiz.spotbiz_backend_springboot.service.NotificationService;
import com.spotbiz.spotbiz_backend_springboot.templates.MailTemplate;
import com.spotbiz.spotbiz_backend_springboot.util.EncryptionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("api/v1/coupon")
public class CouponController {

    private final CouponService couponService;
    private final NotificationService notificationService;
    private final MailService mailService;
    private final UserRepo userRepo;
    private final EncodedCouponService encodedCouponService;

    public CouponController(CouponService couponService, NotificationService notificationService, MailService mailService, UserRepo userRepo, EncodedCouponService encodedCouponService) {
        this.couponService = couponService;
        this.notificationService = notificationService;
        this.mailService = mailService;
        this.userRepo = userRepo;
        this.encodedCouponService = encodedCouponService;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertCoupon(@RequestBody CouponDto couponDto) {
        int insertedCoupon = couponService.insertCoupon(couponDto);
        if(insertedCoupon > 0) {
            return ResponseEntity.ok().body(insertedCoupon);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insert coupon failed");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCoupons() {
        List<Coupon> couponList = couponService.getAllCouponDetails();
        if(couponList != null) {
            return ResponseEntity.ok().body(couponList);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check coupon failed");
    }

    @GetMapping("/check/{scannedText}")
    public ResponseEntity<?> checkCoupon(@PathVariable String scannedText) {
        EncodedCoupon encodedCoupon = encodedCouponService.getEncodedCouponById(scannedText);
        if(encodedCoupon != null) {
            return ResponseEntity.ok().body(encodedCoupon);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check coupon failed");
    }

    @PutMapping("/issue/{user_id}/{coupon_id}")
    public ResponseEntity<?> issueCoupon(@PathVariable int user_id, @PathVariable int coupon_id) {
        try{
            int issueCoupon = couponService.issueCoupon(user_id, coupon_id);
            if(issueCoupon > 0) {
                String title = "Congrats!🥳";
                String body = "You've earned a discount! Check your email!";
                notificationService.sendNotification(title, body, NotificationType.COUPON, user_id);

                User user = userRepo.findByUserId(user_id);
                Coupon coupon = couponService.findByCouponId(coupon_id);
                String encryptedCouponId = encodedCouponService.insertEncodedCoupon(coupon);
                String subject = "Discount Coupon!";
                String htmlContent = MailTemplate.getCouponEmail(user.getName(), encryptedCouponId, coupon.getDiscount());
                mailService.sendHtmlMail(user.getEmail(), subject, htmlContent);
                System.out.println(issueCoupon);
                return ResponseEntity.ok().body(issueCoupon);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Issue coupon failed");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user: " + e.getMessage());
        }
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

package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessService;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessVerifyService;
import com.spotbiz.spotbiz_backend_springboot.service.MailService;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import com.spotbiz.spotbiz_backend_springboot.templates.MailTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    private final UserService userService;

    private final BusinessVerifyService businessVerifyService;

    private final BusinessService businessService;

    private final MailService mailService;

    public AdminController(UserService userService, BusinessVerifyService businessVerifyService, MailService mailService, BusinessService businessService) {
        this.userService = userService;
        this.businessVerifyService = businessVerifyService;
        this.mailService = mailService;
        this.businessService = businessService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/check_registration")
    public ResponseEntity<?> checkRegistration(){
        try {
            return ResponseEntity.ok(businessVerifyService.getUnverifiedBusinesses());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Failed to retrieve unverified businesses: " + ex.getMessage());
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> customers = userService.getAllCustomers();

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/business")
    public ResponseEntity<?>getAllBusinesses() {
        List<Business> businesses = businessService.getAllBusinesses();

        return ResponseEntity.ok(businesses);
    }


    @GetMapping("/verify/{businessId}")
    public ResponseEntity<?> verifyBusiness(@PathVariable String businessId) {

//        return ResponseEntity.ok("hello");
        try {
            Business business = businessVerifyService.verfiyBusiness(Integer.valueOf(businessId));
            String subject = "Your Business has been verified";
            String htmlContent = MailTemplate.getBusinessVerificationEmail(business.getUser().getName(), business.getName());
            mailService.sendHtmlMail(business.getUser().getEmail(), subject, htmlContent);
            return ResponseEntity.ok("Verification email sent successfully");


//            return ResponseEntity.ok(businessService.verfiyBusiness(Integer.valueOf(businessId)));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Failed to verify business: " + ex.getMessage());
        }
    }
}

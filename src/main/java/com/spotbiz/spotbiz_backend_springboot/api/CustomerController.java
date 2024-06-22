package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.UpdateUserRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Status;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.entity.VerificationToken;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import com.spotbiz.spotbiz_backend_springboot.service.MailService;
import com.spotbiz.spotbiz_backend_springboot.service.VerificationTokenService;
import com.spotbiz.spotbiz_backend_springboot.templates.MailTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final UserService userService;
    private final VerificationTokenService tokenService;
    private final MailService mailService;

    public CustomerController(UserService userService, VerificationTokenService tokenService, MailService mailService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        User managedUser;
        try {
            managedUser = userService.register(user);
            VerificationToken token = tokenService.createVerificationToken(managedUser);
            String subject = "Email Verification";
            String htmlContent = MailTemplate.getVerificationEmail(managedUser.getUsername(), token.getToken());
            mailService.sendHtmlMail(managedUser.getEmail(), subject, htmlContent);
            return ResponseEntity.ok("Verification email sent successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyUser(@PathVariable String token) {
        VerificationToken verificationToken = tokenService.getByToken(token);
        System.out.println(token);
        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found or expired");
        }

        User user = verificationToken.getUser();
        user.setStatus(Status.APPROVED);

        UpdateUserRequestDto updateUserRequest = new UpdateUserRequestDto();
        updateUserRequest.setEmail(user.getEmail());
        updateUserRequest.setStatus(user.getStatus());

        User updatedUser;
        try {
            updatedUser = userService.update(updateUserRequest);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user status");
        }

        return ResponseEntity.ok("User verified successfully. Redirect to login.");
    }
}

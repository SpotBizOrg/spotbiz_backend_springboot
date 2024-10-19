package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessSocialLinkDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessSocialLink;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessSocialLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/social_links")
public class BusinessSocialLinkController {

    @Autowired
    private BusinessSocialLinkService service;

    @PutMapping("/{email}")
    public ResponseEntity<List<BusinessSocialLink>> updateBusinessSocialLink(
            @PathVariable String email,
            @RequestBody List<BusinessSocialLinkDto> updatedLink) {
        List<BusinessSocialLink> updated = service.UpdateSocialLinks(email, updatedLink);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<BusinessSocialLink>> getBusinessSocialLink(@PathVariable String email) {
        List<BusinessSocialLink> links = service.getSocialLinks(email);
        return ResponseEntity.ok(links);
    }
}

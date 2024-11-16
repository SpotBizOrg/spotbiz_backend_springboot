package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessSocialLinkDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessSocialLink;

import java.util.List;

public interface BusinessSocialLinkService {
    List<BusinessSocialLink> UpdateSocialLinks(String email, List<BusinessSocialLinkDto> updatedLink);
    List<BusinessSocialLink> getSocialLinks(String email);
}

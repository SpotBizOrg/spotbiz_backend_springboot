package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.SocialLinkType;
import lombok.Data;

@Data
public class BusinessSocialLinkDto {
    private SocialLinkType type;
    private String link;
}

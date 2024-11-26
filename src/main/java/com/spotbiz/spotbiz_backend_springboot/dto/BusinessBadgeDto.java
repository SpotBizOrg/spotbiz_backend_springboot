package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusinessBadgeDto {

    private int badgeId;
    private int businessId;
    private String businessName;
    private LocalDateTime issuedDate;
}

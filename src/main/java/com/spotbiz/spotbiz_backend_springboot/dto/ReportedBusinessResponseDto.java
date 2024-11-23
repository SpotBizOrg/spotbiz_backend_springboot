package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedBusinessResponseDto {

    private int reportId;
    private String reason;
    private int businessId;
    private String businessName;
}

package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReportResponseDto {
    private int reviewId;
    private int userId;
    private String businessName;
    private String description;
}

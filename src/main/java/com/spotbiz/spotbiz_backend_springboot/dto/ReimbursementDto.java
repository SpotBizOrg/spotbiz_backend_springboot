package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReimbursementDto {
    private int businessId;
    private LocalDateTime dateTime;
    private int[] scannedCouponIds;
}

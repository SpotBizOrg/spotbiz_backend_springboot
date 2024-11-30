package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.ReimbursementStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReimbursementDto {
    private int businessId;
    private LocalDateTime dateTime;
    private float amount;
}

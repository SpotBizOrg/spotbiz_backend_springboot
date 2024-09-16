package com.spotbiz.spotbiz_backend_springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spotbiz.spotbiz_backend_springboot.entity.CouponStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CouponDto {
    private int couponId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
    private String description;
    private float discount;
    private CouponStatus status = CouponStatus.PENDING;
    private int businessId;
    private int userId;
}

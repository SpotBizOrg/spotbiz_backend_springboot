package com.spotbiz.spotbiz_backend_springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spotbiz.spotbiz_backend_springboot.entity.CouponStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationTokenDto {
    private int notificationTokenId;
    private int userId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
    private String token;
}

package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionBillingDto {

    private int subscriptionBillingId;
    private int subscriptionId;
    private int businessId;
    private LocalDateTime billingDate;
    private String billingStatus;
    private Double amount;
    private Boolean isActive;
}

package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionTransactionHistoryDto {

    private int subscriptionBillingId;
    private LocalDateTime billingDate;
    private String billingStatus;
    private double amount;
}

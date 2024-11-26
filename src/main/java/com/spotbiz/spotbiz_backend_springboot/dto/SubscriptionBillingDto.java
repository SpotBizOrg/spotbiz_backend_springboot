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
    private double amount;
    private Boolean isActive;

//    public SubscriptionBillingDto(int subscriptionBillingId, double amount, LocalDateTime billingDate, String billingStatus){
//        this.amount=amount;
//        this.billingDate=billingDate;
//        this.billingStatus=billingStatus;
//        this.subscriptionBillingId=subscriptionBillingId;
//    }
}

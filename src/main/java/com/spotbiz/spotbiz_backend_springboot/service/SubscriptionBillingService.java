package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionBillingDto;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;

import java.util.List;

public interface SubscriptionBillingService {

    SubscriptionBilling insertSubscriptionBilling(SubscriptionBillingDto subscriptionBillingDto);
    List<SubscriptionBillingDto> getAllSubscriptionBillings();
    SubscriptionBillingDto updateSubscriptionBilling(int subscriptionBillingId, SubscriptionBillingDto subscriptionBillingDto);
    int deleteSubscriptionBilling(int subscriptionBillingId);

    SubscriptionBillingDto getSubscriptionBillingById(int subscriptionBillingId);
//    List<SubscriptionBillingDto> getSubscriptionBillingByBusinessId(int businessId);
//    List<SubscriptionBillingDto> getSubscriptionBillingBySubscriptionId(int subscriptionId);
//    List<SubscriptionBillingDto> getSubscriptionBillingByBillingDate(String billingDate);
//    List<SubscriptionBillingDto> getSubscriptionBillingByBillingStatus(LocalDateTime billingStatus);
//    List<SubscriptionBillingDto> getSubscriptionBillingByAmount(Double amount);
}

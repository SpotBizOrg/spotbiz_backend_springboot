package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionBillingDto;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;

import java.util.List;

public interface SubscriptionBillingService {

    SubscriptionBillingDto insertSubscriptionBilling(SubscriptionBillingDto subscriptionBillingDto);

    List<SubscriptionBillingDto> getAllSubscriptionBillings();

    SubscriptionBillingDto updateSubscriptionBilling(int subscriptionBillingId, SubscriptionBillingDto subscriptionBillingDto);

    int deleteSubscriptionBilling(int subscriptionBillingId);

    SubscriptionBillingDto markDeleteSubscriptionBilling(int subscriptionBillingId);

    SubscriptionBillingDto getSubscriptionBillingById(int subscriptionBillingId);
}

package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionBillingDto;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import com.spotbiz.spotbiz_backend_springboot.mapper.SubscriptionBillingMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.SubscriptionBillingRepo;
import com.spotbiz.spotbiz_backend_springboot.service.SubscriptionBillingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionBillingServiceImpl implements SubscriptionBillingService {

    private final SubscriptionBillingRepo subscritionBillingRepo;
    private final SubscriptionBillingMapper subscriptionBillingMapper;

    public SubscriptionBillingServiceImpl(SubscriptionBillingRepo subscritionBillingRepo, SubscriptionBillingMapper subscriptionBillingMapper) {
        this.subscritionBillingRepo = subscritionBillingRepo;
        this.subscriptionBillingMapper = subscriptionBillingMapper;
    }

    @Override
    public SubscriptionBilling insertSubscriptionBilling(SubscriptionBillingDto subscriptionBillingDto) {
        try {
            SubscriptionBilling subscriptionBilling = subscriptionBillingMapper.toSubscriptionBilling(subscriptionBillingDto);
            SubscriptionBilling savedBilling =  subscritionBillingRepo.save(subscriptionBilling);

            if (savedBilling == null) {
                throw new RuntimeException("Error occurred while inserting subscription billing");
            }
            return savedBilling;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while inserting subscription billing");
        }
    }

    @Override
    public List<SubscriptionBillingDto> getAllSubscriptionBillings() {
        return null;
    }

    @Override
    public SubscriptionBillingDto updateSubscriptionBilling(int subscriptionBillingId, SubscriptionBillingDto subscriptionBillingDto) {
        return null;
    }

    @Override
    public int deleteSubscriptionBilling(int subscriptionBillingId) {
        return 0;
    }
}

package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionBillingDto;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import com.spotbiz.spotbiz_backend_springboot.mapper.SubscriptionBillingMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.SubscriptionBillingRepo;
import com.spotbiz.spotbiz_backend_springboot.service.SubscriptionBillingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public SubscriptionBillingDto insertSubscriptionBilling(SubscriptionBillingDto subscriptionBillingDto) {
        try {
            System.out.println(subscriptionBillingDto.getSubscriptionId());
            SubscriptionBilling subscriptionBilling = subscriptionBillingMapper.toSubscriptionBilling(subscriptionBillingDto);
            SubscriptionBilling savedBilling =  subscritionBillingRepo.save(subscriptionBilling);
            SubscriptionBillingDto optimized = subscriptionBillingMapper.toSubscriptionBillingDto(savedBilling);

            if (savedBilling == null) {
                throw new RuntimeException("Error occurred while inserting subscription billing");
            }
            return optimized;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while inserting subscription billing", e);
        }
    }

    @Override
    public List<SubscriptionBillingDto> getAllSubscriptionBillings() {
        try{
            List<SubscriptionBilling> allSubscriptionBillings = subscritionBillingRepo.findAll();
            List<SubscriptionBillingDto> optimizedSubscriptionBillings = new ArrayList<>();
            for (SubscriptionBilling subscriptionBilling : allSubscriptionBillings) {
                SubscriptionBillingDto optimizedSubscriptionBilling = subscriptionBillingMapper.toSubscriptionBillingDto(subscriptionBilling);
                optimizedSubscriptionBillings.add(optimizedSubscriptionBilling);

            }
            return optimizedSubscriptionBillings;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching all subscription billings", e);
        }
    }

    @Override
    public SubscriptionBillingDto updateSubscriptionBilling(int subscriptionBillingId, SubscriptionBillingDto subscriptionBillingDto) {
        try {
            SubscriptionBilling subscriptionBilling = subscritionBillingRepo.findById(subscriptionBillingId).orElse(null);
            if (subscriptionBilling == null) {
                throw new RuntimeException("Subscription billing not found");
            }
            SubscriptionBilling updatedSubscriptionBilling = subscriptionBillingMapper.toSubscriptionBilling(subscriptionBillingDto);
            updatedSubscriptionBilling.setSubscriptionBillingId(subscriptionBillingId);
            SubscriptionBilling savedBilling =  subscritionBillingRepo.save(updatedSubscriptionBilling);
            return subscriptionBillingMapper.toSubscriptionBillingDto(savedBilling);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while updating subscription billing", e);
        }
    }

    @Override
    public int deleteSubscriptionBilling(int subscriptionBillingId) {
        try {
            SubscriptionBilling subscriptionBilling = subscritionBillingRepo.findById(subscriptionBillingId).orElse(null);
            assert subscriptionBilling != null;
            if (subscriptionBilling.getBillingStatus().equals("PAID")) {

                throw new RuntimeException("Subscription billing cannot be deleted");

            }
            subscritionBillingRepo.deleteById(subscriptionBillingId);
            return subscriptionBillingId;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error occurred while deleting subscription billing", e);
        }
    }

    @Override
    public SubscriptionBillingDto markDeleteSubscriptionBilling(int subscriptionBillingId) {
        try {
            SubscriptionBilling subscriptionBilling = subscritionBillingRepo.findById(subscriptionBillingId).orElse(null);
            if (subscriptionBilling == null) {
                throw new RuntimeException("Subscription billing not found");
            }
            subscriptionBilling.setBillingStatus("DELETED");
            subscriptionBilling.setIsActive(false);
            SubscriptionBilling savedBilling =  subscritionBillingRepo.save(subscriptionBilling);
            return subscriptionBillingMapper.toSubscriptionBillingDto(savedBilling);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while marking subscription billing as deleted", e);
        }
    }

    @Override
    public SubscriptionBillingDto getSubscriptionBillingById(int subscriptionBillingId) {
        try {
            SubscriptionBilling subscriptionBilling = subscritionBillingRepo.findById(subscriptionBillingId).orElse(null);
            if (subscriptionBilling == null) {
                throw new RuntimeException("Subscription billing not found");
            }
            return subscriptionBillingMapper.toSubscriptionBillingDto(subscriptionBilling);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching subscription billing");
        }
    }
}

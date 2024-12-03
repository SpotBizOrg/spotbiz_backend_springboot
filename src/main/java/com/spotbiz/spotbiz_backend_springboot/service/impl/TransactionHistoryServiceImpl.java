package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.ReimburementResponseDto;
import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionTransactionHistoryDto;
import com.spotbiz.spotbiz_backend_springboot.entity.ReimbursementStatus;
import com.spotbiz.spotbiz_backend_springboot.entity.Reimbursements;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import com.spotbiz.spotbiz_backend_springboot.repo.ReimbursementRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.SubscriptionBillingRepo;
import com.spotbiz.spotbiz_backend_springboot.service.TransacrionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionHistoryServiceImpl implements TransacrionHistoryService {

    @Autowired
    private SubscriptionBillingRepo subscriptionBillingRepo;

    @Autowired
    private ReimbursementRepo reimbursementRepo;
    @Override
    public List<SubscriptionTransactionHistoryDto> getPastMonthBillings() {
        try {
            LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
            List<SubscriptionBilling> subscriptionBillings = subscriptionBillingRepo.findAllByBillingDateAfter(thirtyDaysAgo);
            if (subscriptionBillings.isEmpty()) {
                throw new RuntimeException("No billings found in the past month");
            }

//            List<SubscriptionTransactionHistoryDto> transactionHistoryDtos = subscriptionBillings.stream()
//                    .map(subscriptionBilling -> new SubscriptionTransactionHistoryDto(subscriptionBilling.getSubscriptionBillingId(), subscriptionBilling.getBillingDate(), subscriptionBilling.getBillingStatus(), subscriptionBilling.getAmount()))
//                    .toList();

            List<SubscriptionTransactionHistoryDto> transactionHistoryDtos = new ArrayList<>();

            for (SubscriptionBilling subscriptionBilling: subscriptionBillings) {
                SubscriptionTransactionHistoryDto subscriptionTransactionHistoryDto = new SubscriptionTransactionHistoryDto();
                subscriptionTransactionHistoryDto.setSubscriptionBillingId(subscriptionBilling.getSubscriptionBillingId());
                subscriptionTransactionHistoryDto.setBillingDate(subscriptionBilling.getBillingDate());
                subscriptionTransactionHistoryDto.setBillingStatus(subscriptionBilling.getBillingStatus());
                subscriptionTransactionHistoryDto.setAmount(subscriptionBilling.getAmount());
                transactionHistoryDtos.add(subscriptionTransactionHistoryDto);

            }

            System.out.println(transactionHistoryDtos.size());
            return transactionHistoryDtos;
        }catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            throw new RuntimeException("Error occurred while fetching the billing data", e);

        }
    }

    @Override
    public List<ReimburementResponseDto> getAllPaidReimbursements() {
        try{
            List<Reimbursements> list= reimbursementRepo.findAllByStatus(ReimbursementStatus.PAYED);
            List<ReimburementResponseDto> responseList = new ArrayList<>();

            for ( Reimbursements reiburement: list) {
                ReimburementResponseDto response = new ReimburementResponseDto();
                response.setId(reiburement.getId());
                response.setAmount(reiburement.getAmount());
                response.setDateTime(reiburement.getDateTime());
                response.setImages(reiburement.getImages());
                response.setStatus(reiburement.getStatus());
                response.setBusinessName(reiburement.getBusiness().getName());
                responseList.add(response);
            }
            return responseList;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}

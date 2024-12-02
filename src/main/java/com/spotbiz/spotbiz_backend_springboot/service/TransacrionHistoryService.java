package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionTransactionHistoryDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Reimbursements;

import java.util.List;

public interface TransacrionHistoryService {

    List<SubscriptionTransactionHistoryDto> getPastMonthBillings();

    List<Reimbursements> getAllPaidReimbursements();
}

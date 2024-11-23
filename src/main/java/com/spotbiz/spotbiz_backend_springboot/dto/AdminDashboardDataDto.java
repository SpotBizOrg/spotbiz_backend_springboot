package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDataDto {

    private int customerCount;
    private int businessCount;
    private double totalRevenue;
    private List<SubscriptionBillingDto> billingList;
    private Map<String, Long> businessCategoryCount;


}

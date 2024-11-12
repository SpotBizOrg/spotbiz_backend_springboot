package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionBillingDto;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionBillingMapper {

    @Mapping(source = "pkg.packageId", target = "subscriptionId")
    @Mapping(source = "business.businessId", target = "businessId")
    SubscriptionBillingDto toSubscriptionBillingDto(SubscriptionBilling subscriptionBilling);

    @Mapping(source = "subscriptionId", target = "pkg.packageId")
    @Mapping(source = "businessId", target = "business.businessId")
    SubscriptionBilling toSubscriptionBilling(SubscriptionBillingDto subscriptionBillingDto);
}

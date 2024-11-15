package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusinessMapper {
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "subscriptionBilling.subscriptionBillingId", target = "subscriptionBillingId")
    BusinessDto toBusinessDto(Business business);

    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "subscriptionBillingId", target = "subscriptionBilling.subscriptionBillingId")
    Business toBusiness(BusinessDto businessDto);

    @Mapping(source = "subscriptionBillingId", target = "subscriptionBilling.subscriptionBillingId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBusinessFromDto(BusinessDto dto, @MappingTarget Business entity);

}

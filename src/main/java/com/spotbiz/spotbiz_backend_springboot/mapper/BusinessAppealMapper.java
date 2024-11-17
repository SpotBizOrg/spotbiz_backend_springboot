package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessAppealDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessAppeal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusinessAppealMapper {


    BusinessAppealDto toBusinessAppealDto(BusinessAppeal businessAppeal);

    @Mapping(source = "businessId", target = "business.businessId")
    BusinessAppeal toBusinessAppeal(BusinessAppealDto businessAppealDto);
}

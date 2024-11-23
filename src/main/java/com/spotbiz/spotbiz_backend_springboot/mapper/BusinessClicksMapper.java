package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessClicksDto;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessClicks;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusinessClicksMapper {

    @Mapping(source = "businessId", target = "business.businessId")
    BusinessClicks toBusinessClicks(BusinessClicksDto businessClicksDto);

    @Mapping(source = "business.businessId", target = "businessId")
    BusinessClicksDto toBusinessClicksDto(BusinessClicks businessClicks);
}

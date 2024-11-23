package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.ReportedBusinessDto;
import com.spotbiz.spotbiz_backend_springboot.dto.ReportedBusinessResponseDto;
import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportedBusinessMappr {

    @Mapping(source = "businessId", target = "business.businessId")
    @Mapping(source = "userId", target = "user.userId")
    ReportedBusiness toReportedBusiness(ReportedBusinessDto reportedBusiness);

    @Mapping(source = "business.businessId", target = "businessId")
    @Mapping(source = "user.userId", target = "userId")
    ReportedBusinessDto toReportedBusinessDto(ReportedBusiness reportedBusiness);

    @Mapping(source = "business.businessId", target = "businessId")
    @Mapping(source = "business.name", target = "businessName")
    ReportedBusinessResponseDto toReportedBusinessResponseDto(ReportedBusiness reportedBusiness);
}

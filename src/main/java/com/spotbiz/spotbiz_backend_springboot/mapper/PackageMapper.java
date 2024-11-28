package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.PackageDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)

public interface PackageMapper {

    @Mapping(target = "packageId", source = "packageId")
    PackageDto toPackageDto(Package pkg);
}

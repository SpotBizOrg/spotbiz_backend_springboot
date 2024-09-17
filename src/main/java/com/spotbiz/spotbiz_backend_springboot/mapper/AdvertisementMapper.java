package com.spotbiz.spotbiz_backend_springboot.mapper;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementDto;
import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRecommendationDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.sql.ResultSet;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdvertisementMapper{

    AdvertisementDto toAdvertisementDto(Advertisement advertisement);
    Advertisement toAdvertisement(AdvertisementDto advertisementDto);
    List<AdvertisementDto> mapToAdvertisementDtos(List<Advertisement> advertisements);
    List<Advertisement> mapToAdvertisements(List<AdvertisementDto> advertisementDtos);

}

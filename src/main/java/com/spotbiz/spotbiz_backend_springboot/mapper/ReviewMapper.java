package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.ReviewRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "businessId", target = "business.businessId")
    Review toReviewEntity(ReviewRequestDto dto);

    ReviewRequestDto toReviewRequestDto(Review entity);
}

package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.ReviewDto;
import com.spotbiz.spotbiz_backend_springboot.dto.ReviewReportResponseDto;
import com.spotbiz.spotbiz_backend_springboot.dto.ReviewRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "businessId", target = "business.businessId")
    Review toReviewEntity(ReviewRequestDto dto);

    @Mapping(source = "user.name", target = "username")
    @Mapping(source = "business.businessId", target = "businessId")
    ReviewDto toReviewRequestDto(Review review);

    List<ReviewDto> toReviewRequestDtoList(List<Review> reviews);



    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "business.name", target = "businessName")
    ReviewReportResponseDto toReviewReportResponseDto(Review review);
}

package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import com.spotbiz.spotbiz_backend_springboot.entity.AdvertisementKeyword;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementRecommendationDto {
    private Integer adsId;
    private String data;
    private Boolean status;
    private Integer businessId;
    private String tags;
}

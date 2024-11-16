package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PackageDto {

    private int packageId;
    private String feature;
    private int adsPerWeek;
    private Boolean analytics;
    private Boolean fakeReviews;
    private Boolean recommendation;
    private Boolean messaging;
    private Double price;
    private String listing;
    private Boolean isActive;
}

package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessBoxDto {

    private Integer businessId;
    private String name;
    private String logo;
    private String description;
//    private String locationUrl;
    private String address;
//    private List<String> badges;
    private Double avgRating;
    private String status;
}

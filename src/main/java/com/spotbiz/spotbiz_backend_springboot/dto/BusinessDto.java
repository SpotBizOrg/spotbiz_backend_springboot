package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.Category;
import lombok.Data;

@Data
public class BusinessDto {
    private Integer businessId;
    private String businessRegNo;
    private String name;
    private String logo;
    private String profileCover;
    private String description;
    private String locationUrl;
    private String contactNo;
    private String address;
    private String status;
    private Integer userId;
    private Category category;


}

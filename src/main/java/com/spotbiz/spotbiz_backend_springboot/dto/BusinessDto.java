package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

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
    private Integer categoryId;
    private List<String> tags;


}

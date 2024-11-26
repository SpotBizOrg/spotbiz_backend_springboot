package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessAdminResponseDto {

    private int businessId;
    private String name;
    private String email;
    private String contactNo;
    private String status;
    private String address;

}

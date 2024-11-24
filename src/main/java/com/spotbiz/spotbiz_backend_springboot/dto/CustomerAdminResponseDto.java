package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAdminResponseDto {

    private int userId;
    private String name;
    private String email;
    private double score;
    private String phoneNo;

}

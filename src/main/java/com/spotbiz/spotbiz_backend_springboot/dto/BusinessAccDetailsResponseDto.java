package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessAccDetailsResponseDto {

    private int id;
    private String accountNo;
    private String accountHolderName;
    private String bankName;
    private String branchName;
    private String phoneNo;
    private String email;
    private String businessName;
}

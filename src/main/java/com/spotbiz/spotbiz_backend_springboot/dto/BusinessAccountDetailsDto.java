package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessAccountDetailsDto {
    private int businessId;
    private String accountNo;
    private String accountHolderName;
    private String bankName;
    private String branchName;
}

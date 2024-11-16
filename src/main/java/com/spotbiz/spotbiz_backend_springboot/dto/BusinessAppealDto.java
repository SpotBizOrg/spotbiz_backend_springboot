package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessAppealDto {

    private Integer appealId;
    private String reason;
    private Integer businessId;
    private String status;
}

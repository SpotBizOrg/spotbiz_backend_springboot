package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportedBusinessDto {

    private Integer reportId;
    private String reason;
    private Integer businessId;
    private Integer userId;
}

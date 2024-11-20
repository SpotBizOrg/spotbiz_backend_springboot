package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.Package;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDashboardDto {
    private int businessId;
    private int clickCount;
    private int subscriberCount;
    private PackageDto pkg;
    private String logo;


}

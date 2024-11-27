package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.Package;
import com.spotbiz.spotbiz_backend_springboot.entity.Subscribe;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDashboardDto {
    private int businessId;
    private String email;
    private int clickCount;
    private int subscriberCount;
    private PackageDto pkg;
    private String logo;
    private List<SubscribeDto> subscribeList;
    private BusinessBadgeDto businessBadgeDto;


}

package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessClicksDto {

    private int businessClickId;
    private int businessId;
    private int clicks;
}

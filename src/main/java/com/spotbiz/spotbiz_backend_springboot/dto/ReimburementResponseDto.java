package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReimburementResponseDto {

    private int id;
    private String businessName;
    private LocalDateTime dateTime;
    private float amount;
    private String images;


}

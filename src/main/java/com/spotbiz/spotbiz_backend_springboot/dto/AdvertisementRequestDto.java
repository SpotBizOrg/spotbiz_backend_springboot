package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AdvertisementRequestDto {
    private String description;
    private String img;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean status;
    private Integer BusinessId;
    private List<String> tags;
}
